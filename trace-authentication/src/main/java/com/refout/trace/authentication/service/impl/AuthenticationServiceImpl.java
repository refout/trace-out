package com.refout.trace.authentication.service.impl;

import com.refout.trace.authentication.constant.CacheKey;
import com.refout.trace.authentication.domain.*;
import com.refout.trace.authentication.service.AuthenticationService;
import com.refout.trace.authentication.util.CaptchaGenerator;
import com.refout.trace.authentication.util.jwt.JwtUtil;
import com.refout.trace.common.system.domain.User;
import com.refout.trace.common.system.domain.authenticated.Authenticated;
import com.refout.trace.common.system.service.MenuService;
import com.refout.trace.common.system.service.UserService;
import com.refout.trace.common.util.RandomUtil;
import com.refout.trace.common.util.StringUtil;
import com.refout.trace.common.web.exception.AuthorizationException;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.TimeUnit;


/**
 * TODO
 *
 * @author oo w
 * @version 1.0
 * @since 2023/7/26 11:51
 */
@Slf4j
@Service
public class AuthenticationServiceImpl implements AuthenticationService {


    @Value("${trace.token.expiration-second}")
    private int tokenExpirationSecond;

    @Value("${trace.captcha.expiration-second}")
    private int captchaExpirationSecond;

    @Value("${trace.captcha.enable}")
    private boolean captchaEnable;

    @Value("${trace.login.password-error.retry-count}")
    private int passwordErrorRetryCount;

    @Value("${trace.login.password-error.lock-minute}")
    private int passwordErrorLockMinute;

    @Resource
    private RedisTemplate<String, String> redisTemplateStr;

    @Resource
    private RedisTemplate<String, Integer> redisTemplateInt;

    @Resource
    private RedisTemplate<String, Authenticated> redisTemplate;

    @Resource
    private UserService userService;

    @Resource
    private MenuService menuService;

    /**
     * 验证码生成
     *
     * @return 验证码响应数据
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
     *
     * @param loginRequest 登录请求信息
     * @return 登录响应
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

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(credentials, user.getPassword())) {
            recordRetry(principal, retryLoginCount);
            throw new AuthorizationException("用户名或密码错误");
        }

        removeRetry(principal);

        return user;
    }

    private int getRetry(final String username) {
        String passwordErrorCountKey = CacheKey.passwordErrorCountKey(username);
        Integer count = redisTemplateInt.boundValueOps(passwordErrorCountKey).get();
        if (count == null) {
            count = 0;
        }
        return count;
    }

    private void recordRetry(final String username, final int count) {
        String passwordErrorCountKey = CacheKey.passwordErrorCountKey(username);
        redisTemplateInt.boundValueOps(passwordErrorCountKey)
                .set(count + 1, passwordErrorLockMinute, TimeUnit.MINUTES);
    }

    private void removeRetry(final String username) {
        String passwordErrorCountKey = CacheKey.passwordErrorCountKey(username);
        redisTemplateInt.delete(passwordErrorCountKey);
    }

    private String buildToken(final User user) {
        Long id = user.getId();
        if (id == null) {
            throw new AuthorizationException();
        }
        Set<String> permissions = menuService.getPermissionByUserId(id);

        String tokenId = RandomUtil.randomUUID();
        String token = JwtUtil.createToken(tokenId);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expirationTime = now.plusSeconds(tokenExpirationSecond);
        Authenticated authenticated = new Authenticated(
                token, user, permissions, now, expirationTime,
                //todo
                "", "", "", ""
        );
        redisTemplate.boundValueOps(CacheKey.userKey(tokenId))
                .set(authenticated, tokenExpirationSecond, TimeUnit.SECONDS);
        return token;
    }

    /**
     * 注册
     *
     * @param registerRequest 注册请求信息
     * @return 注册响应
     */
    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {
        return null;
    }

}
