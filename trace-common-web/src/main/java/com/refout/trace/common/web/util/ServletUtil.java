package com.refout.trace.common.web.util;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


/**
 * 客户端工具类
 *
 * @author oo w
 * @version 1.0
 * @since 2023/7/27 22:43
 */
@Slf4j
public class ServletUtil {

    private static final String USER_AGENT = "User-Agent";

    public static @NotNull String getRequestIP() {
        return IpUtil.getIpAddr(ServletUtil.getRequest());
    }

    public static @Nullable String getUserAgent() {
        return getHeader(USER_AGENT);
    }

    public static @Nullable String getHeader(String name) {
        HttpServletRequest request = getRequest();
        if (request == null) {
            return null;
        }

        return request.getHeader(name);
    }

    /**
     * 获取request
     */
    public static @Nullable HttpServletRequest getRequest() {
        try {
            ServletRequestAttributes attributes = getRequestAttributes();
            if (attributes != null) {
                return attributes.getRequest();
            }
        } catch (Exception e) {
            log.error("获取HttpServletRequest失败", e);
        }
        return null;
    }

    /**
     * 获取response
     */
    public static @Nullable HttpServletResponse getResponse() {
        try {
            ServletRequestAttributes attributes = getRequestAttributes();
            if (attributes != null) {
                return attributes.getResponse();
            }
        } catch (Exception e) {
            log.error("获取请求属性失败", e);
        }
        return null;
    }

    public static @Nullable ServletRequestAttributes getRequestAttributes() {
        try {
            RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
            if (attributes == null) {
                log.error("获取请求属性为空");
                return null;
            }
            return (ServletRequestAttributes) attributes;
        } catch (Exception e) {
            log.error("获取请求属性失败", e);
            return null;
        }
    }

}
