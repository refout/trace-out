package com.refout.trace.common.system.domain.authenticated;

import com.refout.trace.common.system.domain.User;

import java.time.LocalDateTime;
import java.util.Set;

public record Authenticated(String token,
                            User user,
                            Set<String> permissions,
                            LocalDateTime loginTime,
                            LocalDateTime expireTime,
                            String ip,
                            String browser,
                            String os) {

}
