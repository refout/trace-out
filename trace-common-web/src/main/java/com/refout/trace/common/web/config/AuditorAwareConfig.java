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

@Slf4j
@Configuration
@EnableJpaAuditing
public class AuditorAwareConfig implements AuditorAware<String> {

    @Override
    public @NotNull Optional<String> getCurrentAuditor() {
        try {
            Authenticated authenticated = AuthenticatedContextHolder.getContext();
            if (authenticated == null || authenticated.user() == null ||
                    !StringUtil.hasText(authenticated.user().getUsername())) {
                log.error("审计功能，当前操作用户为空");
                throw new SystemException("操作失败，获取当前操作用户的值为空");
            }
            return Optional.of(authenticated.user().getUsername());
        } catch (Exception ex) {
            log.error("审计功能，获取当前用户发生异常：" + ex.getMessage(), ex);
            throw new SystemException("操作失败，无法获取当前操作用户");
        }
    }

}
