package com.refout.trace.common.web.util;

import com.refout.trace.common.web.domain.Result;
import jakarta.annotation.Nonnull;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * TODO
 *
 * @author oo w
 * @version 1.0
 * @since 2023/5/12 17:16
 */
public class ResponseUtil {

    public static void response(@Nonnull HttpServletResponse response,
                                @Nonnull Result result) {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.OK.value());
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        try {
            response.getWriter().print(result.toJson());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void response(@Nonnull HttpServletResponse response,
                                int code, String msg) {
        response(response, new Result(code, msg));
    }

    public static void response(@Nonnull HttpServletResponse response,
                                HttpStatus code, String msg) {
        response(response, new Result(code.value(), msg));
    }

}
