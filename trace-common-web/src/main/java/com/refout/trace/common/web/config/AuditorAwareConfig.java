package com.refout.trace.common.web.config;

import com.refout.trace.common.util.StrUtil;
import com.refout.trace.common.web.context.AuthenticatedContextHolder;
import com.refout.trace.common.web.domain.Authenticated;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

/**
 * 审计配置
 *
 * @author oo w
 * @version 1.0
 * @since 2023/8/1 8:46
 */
@Slf4j
@Configuration
@EnableJpaAuditing
public class AuditorAwareConfig implements AuditorAware<String> {

    /**
     * 审计默认值
     */
    private static final String DEFAULT_USER = "system";

    /**
     * 获取当前的审计人员，当前操作用户
     *
     * @return 当前的审计人员，当前操作用户
     */
    @Override
    public @NotNull Optional<String> getCurrentAuditor() {
        Authenticated authenticated = AuthenticatedContextHolder.getContext();
        if (authenticated == null || authenticated.getUser() == null ||
                !StrUtil.hasText(authenticated.getUser().getUsername())) {
            log.error("审计功能，当前操作用户为空，使用默认值填充：{}", DEFAULT_USER);

            return Optional.of(DEFAULT_USER);
        }
        return Optional.of(authenticated.getUser().getUsername());
    }

}
