package com.refout.trace.authentication.service.impl;

import com.refout.trace.authentication.config.AuthenticationConfig;
import com.refout.trace.authentication.constant.CacheKey;
import com.refout.trace.authentication.domain.*;
import com.refout.trace.authentication.service.AuthenticationService;
import com.refout.trace.authentication.util.CaptchaGenerator;
import com.refout.trace.authentication.util.PasswordValidator;
import com.refout.trace.common.system.domain.User;
import com.refout.trace.common.system.service.ApiService;
import com.refout.trace.common.system.service.UserService;
import com.refout.trace.common.util.Assert;
import com.refout.trace.common.util.RandomUtil;
import com.refout.trace.common.util.StrUtil;
import com.refout.trace.common.web.config.CommonConfig;
import com.refout.trace.common.web.constant.AuthCacheKey;
import com.refout.trace.common.web.context.AuthenticatedContextHolder;
import com.refout.trace.common.web.domain.Authenticated;
import com.refout.trace.common.web.exception.AuthenticationException;
import com.refout.trace.common.web.util.ServletUtil;
import com.refout.trace.common.web.util.jwt.JwtUtil;
import com.refout.trace.redis.handler.RedisRetry;
import eu.bitwalker.useragentutils.UserAgent;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
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
     * 字符串类型的Redis模板
     */
    @Resource
    private RedisTemplate<String, String> redisTemplateStr;

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
        if (!AuthenticationConfig.CAPTCHA_ENABLE.value()) {
            log.debug("未开启验证码，不产生验证码");
            return new CaptchaResponse(null, null);
        }
        CaptchaGenerator.Captcha generate = CaptchaGenerator.generate();
        String captchaId = RandomUtil.randomUUID();
        redisTemplateStr.opsForValue().set(
                CacheKey.captchaKey(captchaId), generate.captchaText(),
                AuthenticationConfig.CAPTCHA_EXPIRATION_SECOND.value(), TimeUnit.SECONDS
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
        Assert.notNull(AuthenticationException::new, loginRequest, "登录请求数据不能为空");

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
        if (AuthenticationConfig.CAPTCHA_ENABLE.value()) {
            String captchaId = loginRequest.captchaId();

            String captchaErrorCountKey = CacheKey.captchaErrorCountKey(captchaId);

            Assert.isTrue(
                    AuthenticationException::new,
                    //获取指定用户密码错误的重试登录次数
                    RedisRetry.getRetry(captchaErrorCountKey) < AuthenticationConfig.CAPTCHA_ERROR_RETRY_COUNT.value(),
                    "验证码多次输入错误，账号已被锁定，稍后再试"
            );

            String captchaCode = loginRequest.captchaCode();
            Assert.isTrue(AuthenticationException::new, StrUtil.hasTextAll(captchaId, captchaCode), () -> {
                RedisRetry.recordRetry(captchaErrorCountKey, AuthenticationConfig.CAPTCHA_ERROR_LOCK_MINUTE.value(), TimeUnit.MINUTES);
                return "验证码为空";
            });

            String key = CacheKey.captchaKey(captchaId);
            String cacheCaptchaCode = redisTemplateStr.opsForValue().get(key);
            Assert.hasText(AuthenticationException::new, cacheCaptchaCode, "验证码已失效");
            Assert.isTrue(AuthenticationException::new, captchaCode.equalsIgnoreCase(cacheCaptchaCode), "验证码错误");

            RedisRetry.removeRetry(captchaErrorCountKey);
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
        Assert.isTrue(
                AuthenticationException::new, StrUtil.hasTextAll(principal, credentials), "用户名或密码为空"
        );

        // 获取密码错误计数键
        String passwordErrorCountKey = CacheKey.passwordErrorCountKey(principal);

        Assert.isTrue(
                AuthenticationException::new,
                //获取指定用户密码错误的重试登录次数
                RedisRetry.getRetry(passwordErrorCountKey) < AuthenticationConfig.PASSWORD_ERROR_RETRY_COUNT.value(),
                "用户名或密码多次输入错误，账号已被锁定，稍后再试"
        );

        User user = userService.getByUsername(principal);
        if (user == null) {
            user = userService.getByPhone(principal);
        }

        Assert.notNull(AuthenticationException::new, user, "用户未注册");

        Assert.isTrue(AuthenticationException::new, PasswordValidator.matches(credentials, user.getPassword()), () -> {
            // 将重试次数加1，并设置到Redis中，设置过期时间为passwordErrorLockMinute分钟
            RedisRetry.recordRetry(passwordErrorCountKey, AuthenticationConfig.PASSWORD_ERROR_LOCK_MINUTE.value(), TimeUnit.MINUTES);
            return "用户名或密码错误";
        });

        // 移除用户密码错误的重试登录次数
        RedisRetry.removeRetry(passwordErrorCountKey);

        return user;
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
        // 如果用户ID为空，则抛出AuthenticationException异常
        Assert.notNull(AuthenticationException::new, id, "用户信息获取失败");
        // 根据用户ID获取用户的权限接口列表
        List<String> permissions = apiService.getPermissionByUserId(id);
        // 生成一个随机的token ID
        String tokenId = RandomUtil.randomUUID();
        // 根据token ID生成JWT token字符串
        String token = JwtUtil.createToken(tokenId);
        // 获取当前时间
        LocalDateTime now = LocalDateTime.now();
        // 计算token的过期时间
        LocalDateTime expirationTime = now.plusSeconds(CommonConfig.TOKEN_EXPIRATION_SECOND.value());
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
                .set(authenticated, CommonConfig.TOKEN_EXPIRATION_SECOND.value(), TimeUnit.SECONDS);
        // 返回JWT token字符串
        return token;
    }

    /**
     * 登出
     */
    @Override
    public void logout() {
        Authenticated context = AuthenticatedContextHolder.getContext();
        Assert.notNull(AuthenticationException::new, context, "已注销");

        String userKey = AuthCacheKey.userKeyFromToken(context.getToken());
        Assert.hasText(AuthenticationException::new, userKey, "已注销");

        Boolean delete = redisTemplateAuthenticated.delete(userKey);
        Assert.isTrue(AuthenticationException::new, Boolean.TRUE.equals(delete), "注销失败");
    }

    /**
     * 注册
     *
     * @param request 注册请求信息
     * @return 注册响应
     */
    @Override
    public RegisterResponse register(@Nullable RegisterRequest request) {
        Assert.notNull(AuthenticationException::new, request, "注册信息不能为空");
        String username = request.username();
        String password = request.password();

        Assert.isTrue(AuthenticationException::new, StrUtil.hasTextAll(username, password), "用户名或密码不能为空");
        Assert.isTrue(AuthenticationException::new, !StrUtil.containsWhitespace(username), "用户名不能包含空格");
        Assert.isTrue(AuthenticationException::new, !StrUtil.containsWhitespace(password), "密码不能包含空格");
        Assert.isTrue(
                AuthenticationException::new,
                username.length() >= USERNAME_MIN_LEN && username.length() <= USERNAME_MAX_LEN,
                String.format("用户名长度在 %s ~ %s 之间", USERNAME_MIN_LEN, USERNAME_MAX_LEN)
        );
        Assert.isTrue(AuthenticationException::new, PasswordValidator.isPasswordValid(password), "密码强度不满足要求");
        Assert.isTrue(AuthenticationException::new, !userService.existWithUsername(username), "用户名已注册");
        Assert.isTrue(AuthenticationException::new, !userService.existWithPhone(username), "手机号作为用户名已注册");
        Assert.isTrue(AuthenticationException::new, !userService.existWithPhone(request.phone()), "手机号已注册");

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
