package com.refout.trace.common.web.util;

import com.refout.trace.common.web.domain.Result;
import jakarta.annotation.Nonnull;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 这是一个用于处理HTTP响应的实用类。
 * 它提供了三个方法来向客户端发送响应。
 *
 * @author oo w
 * @version 1.0
 * @since 2023/5/12 17:16
 */
public class ResponseUtil {

    /**
     * 发送带有Result对象的响应。
     *
     * @param response HttpServletResponse对象
     * @param result   Result对象
     */
    public static void response(@Nonnull HttpServletResponse response,
                                @Nonnull Result result) {
        // 设置内容类型为JSON
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        // 设置HTTP状态为200 OK
        response.setStatus(HttpStatus.OK.value());
        // 设置字符编码为UTF-8
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        try {
            // 将Result对象的JSON表示写入响应的输出流
            response.getWriter().print(result.toJson());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 发送带有代码和消息的响应。
     *
     * @param response HttpServletResponse对象
     * @param code     响应代码
     * @param msg      消息字符串
     */
    public static void response(@Nonnull HttpServletResponse response,
                                int code, String msg) {
        // 调用response()方法，使用code和msg创建一个新的Result对象
        response(response, new Result(code, msg));
    }

    /**
     * 发送带有HttpStatus和消息的响应。
     *
     * @param response HttpServletResponse对象
     * @param code     HttpStatus对象
     * @param msg      消息字符串
     */
    public static void response(@Nonnull HttpServletResponse response,
                                @NotNull HttpStatus code, String msg) {
        // 调用response()方法，使用HttpStatus对象的value字段的值和msg创建一个新的Result对象
        response(response, new Result(code.value(), msg));
    }

}