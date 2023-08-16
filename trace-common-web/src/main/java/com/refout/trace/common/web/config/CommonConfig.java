package com.refout.trace.common.web.config;

import com.refout.trace.common.system.config.AbstractConfig;
import com.refout.trace.common.system.config.Property;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CommonConfig extends AbstractConfig {

    /**
     * token过期时间
     */
    public final static Property<Long> TOKEN_EXPIRATION_SECOND =
            Property.of("trace.token.expiration-second", 1800L, "认证", "token过期时间", Long.class);

}
