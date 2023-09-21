package com.refout.trace.authentication.config;

import com.refout.trace.common.system.config.AbstractConfig;
import com.refout.trace.common.system.config.Property;
import com.refout.trace.common.util.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 认证配置类，用于管理认证相关的配置属性。
 *
 * @author oo w
 * @version 1.0
 * @since 2023/9/21 18:34
 */
@Slf4j
@Component
public class AuthenticationConfig extends AbstractConfig {

    /**
     * 应用名
     */
    private static final String applicationName = SpringUtil.getApplicationName();

    /**
     * 验证码过期时间
     */
    public final static Property<Long> CAPTCHA_EXPIRATION_SECOND =
            Property.of("trace.captcha.expiration-second", 300L, applicationName,
                    "验证码", "验证码过期时间", Long.class);

    /**
     * 验证码开关
     */
    public final static Property<Boolean> CAPTCHA_ENABLE =
            Property.of("trace.captcha.enable", false, applicationName,
                    "验证码", "验证码开关", Boolean.class);

    /**
     * 验证码错误锁定时间
     */
    public final static Property<Integer> CAPTCHA_ERROR_LOCK_MINUTE =
            Property.of("trace.captcha.error.lock.minute", 5, applicationName,
                    "验证码", "验证码错误锁定时间", Integer.class);

    /**
     * 验证码开关错误重试次数
     */
    public final static Property<Integer> CAPTCHA_ERROR_RETRY_COUNT =
            Property.of("trace.captcha.error.retry.count", 5, applicationName,
                    "验证码", "验证码错误重试次数", Integer.class);

    /**
     * 密码错误锁定时间
     */
    public final static Property<Integer> PASSWORD_ERROR_LOCK_MINUTE =
            Property.of("trace.login.password.error.lock.minute", 10, applicationName,
                    "密码", "密码错误锁定时间", Integer.class);

    /**
     * 密码错误重试次数
     */
    public final static Property<Integer> PASSWORD_ERROR_RETRY_COUNT =
            Property.of("trace.login.password.error.retry.count", 5, applicationName,
                    "密码", "密码错误重试次数", Integer.class);

}
