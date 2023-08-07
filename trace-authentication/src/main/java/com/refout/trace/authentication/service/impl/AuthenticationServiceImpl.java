package com.refout.trace.authentication.service.impl;

import com.refout.trace.authentication.constant.CacheKey;
import com.refout.trace.authentication.domain.*;
import com.refout.trace.authentication.service.AuthenticationService;
import com.refout.trace.authentication.util.CaptchaGenerator;
import com.refout.trace.authentication.util.PasswordValidator;
import com.refout.trace.common.system.domain.User;
import com.refout.trace.common.system.domain.authenticated.Authenticated;
import com.refout.trace.common.system.service.ApiService;
import com.refout.trace.common.system.service.UserService;
import com.refout.trace.common.util.RandomUtil;
import com.refout.trace.common.util.StrUtil;
import com.refout.trace.common.web.exception.AuthenticationException;
import com.refout.trace.common.web.util.ServletUtil;
import com.refout.trace.common.web.util.jwt.JwtUtil;
import eu.bitwalker.useragentutils.UserAgent;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
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
     * 用户名最小长度
     */
    private static final int USERNAME_MIN_LEN = 3;

    /**
     * 用户名最大长度
     */
    private static final int USERNAME_MAX_LEN = 12;

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
     * 接口服务
     */
    @Resource
    private ApiService apiService;

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
            throw new AuthenticationException("登录请求数据不能为空");
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
            if (!StrUtil.hasTextAll(captchaId, captchaCode)) {
                throw new AuthenticationException("验证码为空");
            }

            String key = CacheKey.captchaKey(captchaId);
            String cacheCaptchaCode = redisTemplateStr.opsForValue().get(key);
            if (!StrUtil.hasText(cacheCaptchaCode)) {
                throw new AuthenticationException("验证码已失效");
            }
            if (!captchaCode.equalsIgnoreCase(cacheCaptchaCode)) {
                throw new AuthenticationException("验证码错误");
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
        if (!StrUtil.hasTextAll(principal, credentials)) {
            throw new AuthenticationException("用户名或密码为空");
        }

        int retryLoginCount = getRetry(principal);
        if (retryLoginCount >= passwordErrorRetryCount) {
            throw new AuthenticationException("用户名或密码多次输入错误，账号已被锁定，稍后再试");
        }

        User user = userService.getByUsername(principal);
        if (user == null) {
            user = userService.getByPhone(principal);
        }
        if (user == null) {
            throw new AuthenticationException("用户未注册");
        }


        if (!PasswordValidator.matches(credentials, user.getPassword())) {
            recordRetry(principal, retryLoginCount);
            throw new AuthenticationException("用户名或密码错误");
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
     * @throws AuthenticationException 如果用户的ID为空，则会抛出此异常
     */
    private String buildToken(final @NotNull User user) throws AuthenticationException {
        // 获取用户的ID
        Long id = user.getId();
        if (id == null) {
            // 如果用户ID为空，则抛出AuthenticationException异常
            throw new AuthenticationException("用户信息获取失败");
        }
        // 根据用户ID获取用户的权限接口列表
        List<String> permissions = apiService.getPermissionByUserId(id);
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
        // 创建一个Authenticated对象，包括token、用户对象、权限接口列表、创建时间、过期时间、请求IP地址、浏览器名称和操作系统名称
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
    public RegisterResponse register(@Nullable RegisterRequest request) {
        assert request != null : new AuthenticationException("注册信息不能为空");

        String username = request.username();
        String password = request.password();

        assert StrUtil.hasTextAll(username, password) : new AuthenticationException("用户名或密码不能为空");
        assert !StrUtil.containsWhitespace(username) : new AuthenticationException("用户名不能包含空格");
        assert !StrUtil.containsWhitespace(password) : new AuthenticationException("密码不能包含空格");

        assert username.length() >= USERNAME_MIN_LEN &&
                username.length() <= USERNAME_MAX_LEN :
                new AuthenticationException(
                        String.format("用户名长度在 %s ~ %s 之间", USERNAME_MIN_LEN, USERNAME_MAX_LEN)
                );

        assert PasswordValidator.isPasswordValid(password) : new AuthenticationException("密码强度不满足要求");
        assert !userService.existWithUsername(username) : new AuthenticationException("用户名已注册");
        assert !userService.existWithPhone(username) : new AuthenticationException("手机号的用户名已注册");
        assert !userService.existWithPhone(request.phone()) : new AuthenticationException("手机号已注册");

        User user = new User()
                .setUsername(username)
                .setPassword(PasswordValidator.encode(password))
                .setNickname(request.nickname())
                .setEmail(request.email())
                .setPhone(request.phone())
                .setGender(request.gender())
                .setAvatar(request.avatar());
        user.setCreateBy(username);
        User save = userService.save(user);
        return new RegisterResponse(save.getUsername());
    }

}
