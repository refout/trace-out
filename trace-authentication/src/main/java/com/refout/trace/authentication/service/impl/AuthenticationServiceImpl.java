package com.refout.trace.authentication.service.impl;

import com.refout.trace.authentication.constant.CacheKey;
import com.refout.trace.authentication.domain.*;
import com.refout.trace.authentication.service.AuthenticationService;
import com.refout.trace.authentication.util.CaptchaGenerator;
import com.refout.trace.common.system.domain.User;
import com.refout.trace.common.system.domain.authenticated.Authenticated;
import com.refout.trace.common.system.service.MenuService;
import com.refout.trace.common.system.service.UserService;
import com.refout.trace.common.util.RandomUtil;
import com.refout.trace.common.util.StringUtil;
import com.refout.trace.common.web.exception.AuthorizationException;
import com.refout.trace.common.web.util.ServletUtil;
import com.refout.trace.common.web.util.jwt.JwtUtil;
import eu.bitwalker.useragentutils.UserAgent;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;


/**
 * 认证服务实现类
 *
 * @author oo w
 * @version 1.0
 * @since 2023/7/26 11:51
 */
@Slf4j
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    /**
     * token过期时间
     */
    @Value("${trace.token.expiration-second}")
    private int tokenExpirationSecond;

    /**
     * 验证码过期时间
     */
    @Value("${trace.captcha.expiration-second}")
    private int captchaExpirationSecond;

    /**
     * 验证码功能开关
     */
    @Value("${trace.captcha.enable}")
    private boolean captchaEnable;

    /**
     * 密码错误重试次数
     */
    @Value("${trace.login.password-error.retry-count}")
    private int passwordErrorRetryCount;

    /**
     * 密码错误锁定时间
     */
    @Value("${trace.login.password-error.lock-minute}")
    private int passwordErrorLockMinute;

    /**
     * 加密
     */
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * 字符串类型的Redis模板
     */
    @Resource
    private RedisTemplate<String, String> redisTemplateStr;

    /**
     * 数字类型的Redis模板
     */
    @Resource
    private RedisTemplate<String, Integer> redisTemplateNumber;

    /**
     * Authenticated类型的Redis模板
     */
    @Resource
    private RedisTemplate<String, Authenticated> redisTemplateAuthenticated;

    /**
     * 用户服务
     */
    @Resource
    private UserService userService;

    /**
     * 菜单服务
     */
    @Resource
    private MenuService menuService;

    /**
     * 验证码生成
     * <p>
     * 如果未开启验证码功能，则直接返回空的验证码响应数据
     * 否则，生成验证码并存入Redis，设置相应的过期时间
     *
     * @return 验证码响应数据，包含验证码ID和验证码图片的Base64编码
     */
    @Override
    public CaptchaResponse captcha() {
        if (!captchaEnable) {
            log.debug("未开启验证码，不产生验证码");
            return new CaptchaResponse(null, null);
        }
        CaptchaGenerator.Captcha generate = CaptchaGenerator.generate();
        String captchaId = RandomUtil.randomUUID();
        redisTemplateStr.opsForValue().set(
                CacheKey.captchaKey(captchaId), generate.captchaText(),
                captchaExpirationSecond, TimeUnit.SECONDS
        );
        return new CaptchaResponse(captchaId, generate.imageBase64());
    }

    /**
     * 登录
     * <p>
     * 首先校验验证码，然后校验用户的用户名和密码
     * 如果校验通过，生成JWT token并返回
     *
     * @param loginRequest 登录请求信息，包含用户名、密码和验证码等
     * @return 登录响应，包含JWT token
     */
    @Override
    public LoginResponse login(final LoginRequest loginRequest) {
        if (loginRequest == null) {
            throw new AuthorizationException("登录请求数据不能为空");
        }

        validateCaptcha(loginRequest);

        User user = validateCredentials(loginRequest);

        String token = buildToken(user);
        return new LoginResponse(token);
    }

    /**
     * 验证码校验
     *
     * @param loginRequest 登录请求
     */
    private void validateCaptcha(final LoginRequest loginRequest) {
        if (captchaEnable) {
            String captchaId = loginRequest.captchaId();
            String captchaCode = loginRequest.captchaCode();
            if (!StringUtil.hasTextAll(captchaId, captchaCode)) {
                throw new AuthorizationException("验证码为空");
            }

            String key = CacheKey.captchaKey(captchaId);
            String cacheCaptchaCode = redisTemplateStr.opsForValue().get(key);
            if (!StringUtil.hasText(cacheCaptchaCode)) {
                throw new AuthorizationException("验证码已失效");
            }
            if (!captchaCode.equalsIgnoreCase(cacheCaptchaCode)) {
                throw new AuthorizationException("验证码错误");
            }

            redisTemplateStr.delete(key);
        }
    }

    /**
     * 登录用户名及密码校验
     *
     * @param loginRequest 登录请求
     * @return 校验通过后的用户信息
     */
    @NotNull
    private User validateCredentials(final @NotNull LoginRequest loginRequest) {
        String principal = loginRequest.principal();
        String credentials = loginRequest.credentials();
        if (!StringUtil.hasTextAll(principal, credentials)) {
            throw new AuthorizationException("用户名或密码为空");
        }

        int retryLoginCount = getRetry(principal);
        if (retryLoginCount >= passwordErrorRetryCount) {
            throw new AuthorizationException("用户名或密码多次输入错误，账号已被锁定，稍后再试");
        }

        User user = userService.getByUsername(principal);
        if (user == null) {
            user = userService.getByPhone(principal);
        }
        if (user == null) {
            throw new AuthorizationException("用户未注册");
        }


        if (!passwordEncoder.matches(credentials, user.getPassword())) {
            recordRetry(principal, retryLoginCount);
            throw new AuthorizationException("用户名或密码错误");
        }

        removeRetry(principal);

        return user;
    }

    /**
     * 获取指定用户密码错误的重试登录次数。
     *
     * @param username 用户名
     * @return 重试次数
     */
    private int getRetry(final String username) {
        // 获取密码错误计数键
        String passwordErrorCountKey = CacheKey.passwordErrorCountKey(username);
        // 从Redis中获取计数值
        Integer count = redisTemplateNumber.boundValueOps(passwordErrorCountKey).get();
        // 如果计数值为空，则将其设置为0
        if (count == null) {
            count = 0;
        }
        // 返回计数值
        return count;
    }

    /**
     * 记录用户密码错误的重试登录次数。
     *
     * @param username 用户名
     * @param count    当前重试次数
     */
    private void recordRetry(final String username, final int count) {
        // 获取密码错误计数键
        String passwordErrorCountKey = CacheKey.passwordErrorCountKey(username);
        // 将重试次数加1，并设置到Redis中，设置过期时间为passwordErrorLockMinute分钟
        redisTemplateNumber.boundValueOps(passwordErrorCountKey)
                .set(count + 1, passwordErrorLockMinute, TimeUnit.MINUTES);
    }

    /**
     * 移除用户密码错误的重试登录次数。
     *
     * @param username 用户名
     */
    private void removeRetry(final String username) {
        // 获取密码错误计数键
        String passwordErrorCountKey = CacheKey.passwordErrorCountKey(username);
        // 从Redis中删除计数键
        redisTemplateNumber.delete(passwordErrorCountKey);
    }

    /**
     * 构建用户的JWT token。
     *
     * @param user 用户对象
     * @return JWT token字符串
     * @throws AuthorizationException 如果用户的ID为空，则会抛出此异常
     */
    private String buildToken(final @NotNull User user) throws AuthorizationException {
        // 获取用户的ID
        Long id = user.getId();
        if (id == null) {
            // 如果用户ID为空，则抛出AuthorizationException异常
            throw new AuthorizationException();
        }
        // 根据用户ID获取用户的权限菜单列表
        List<String> permissions = menuService.getPermissionByUserId(id);
        // 生成一个随机的token ID
        String tokenId = RandomUtil.randomUUID();
        // 根据token ID生成JWT token字符串
        String token = JwtUtil.createToken(tokenId);
        // 获取当前时间
        LocalDateTime now = LocalDateTime.now();
        // 计算token的过期时间
        LocalDateTime expirationTime = now.plusSeconds(tokenExpirationSecond);
        // 获取用户的UserAgent信息
        String userAgent = ServletUtil.getUserAgent();
        UserAgent agent = UserAgent.parseUserAgentString(userAgent);
        // 获取用户的请求IP地址
        String requestIP = ServletUtil.getRequestIP();
        // 创建一个Authenticated对象，包括token、用户对象、权限菜单列表、创建时间、过期时间、请求IP地址、浏览器名称和操作系统名称
        Authenticated authenticated = new Authenticated(
                token, user, new TreeSet<>(permissions), now, expirationTime,
                requestIP, agent.getBrowser().getName(), agent.getOperatingSystem().getName()
        );
        // 将Authenticated对象设置到Redis中，并设置过期时间为tokenExpirationSecond秒
        redisTemplateAuthenticated.boundValueOps(CacheKey.userKey(tokenId))
                .set(authenticated, tokenExpirationSecond, TimeUnit.SECONDS);
        // 返回JWT token字符串
        return token;
    }

    /**
     * 注册
     *
     * @param request 注册请求信息
     * @return 注册响应
     */
    @Override
    public RegisterResponse register(RegisterRequest request) {
        //todo
        User user = new User()
                .setUsername(request.username())
                .setPassword(passwordEncoder.encode(request.password()))
                .setNickname(request.nickname())
                .setEmail(request.email())
                .setPhone(request.phone())
                .setGender(request.gender())
                .setAvatar(request.avatar());
        User save = userService.save(user);
        return new RegisterResponse(save.getUsername());
    }

}
