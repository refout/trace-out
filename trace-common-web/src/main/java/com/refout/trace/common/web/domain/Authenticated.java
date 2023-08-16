package com.refout.trace.common.web.domain;

import com.refout.trace.common.system.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * 认证信息类
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Authenticated {

    /**
     * 认证令牌
     */
    private String token;

    /**
     * 用户对象
     */
    private User user;

    /**
     * 权限集合
     */
    private Set<String> permissions;

    /**
     * 登录时间
     */
    private LocalDateTime loginTime;

    /**
     * 过期时间
     */
    private LocalDateTime expireTime;

    /**
     * IP地址
     */
    private String ip;

    /**
     * 浏览器
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;

}