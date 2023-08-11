package com.refout.trace.authentication.config;

import com.refout.trace.common.system.config.AbstractConfig;
import com.refout.trace.common.system.config.Property;
import com.refout.trace.common.util.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AuthenticationConfig extends AbstractConfig {

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

}
