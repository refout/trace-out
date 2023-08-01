package com.refout.trace.common.web.config;

import com.refout.trace.common.exception.SystemException;
import com.refout.trace.common.system.domain.authenticated.Authenticated;
import com.refout.trace.common.util.StringUtil;
import com.refout.trace.common.web.context.AuthenticatedContextHolder;
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
     * 获取当前的审计人员，当前操作用户
     *
     * @return 当前的审计人员，当前操作用户
     */
    @Override
    public @NotNull Optional<String> getCurrentAuditor() {
        Authenticated authenticated = AuthenticatedContextHolder.getContext();
        if (authenticated == null || authenticated.user() == null ||
                !StringUtil.hasText(authenticated.user().getUsername())) {
            log.error("审计功能，当前操作用户为空");
            throw new SystemException("操作失败，无法获取当前操作用户");
        }
        return Optional.of(authenticated.user().getUsername());
    }

}
