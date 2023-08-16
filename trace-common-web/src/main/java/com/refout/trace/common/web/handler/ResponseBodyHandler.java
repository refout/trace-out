package com.refout.trace.common.web.handler;

import com.refout.trace.common.util.DateUtil;
import com.refout.trace.common.util.JsonUtil;
import com.refout.trace.common.web.filter.JwtFilter;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * ResponseBodyHandler类是一个全局响应处理器，实现了ResponseBodyAdvice接口。
 * 它用于在响应返回之前对响应进行处理，记录请求和响应的相关信息。
 * 使用@Slf4j注解为该类提供日志记录功能。
 * 使用@RestControllerAdvice注解将该类标记为全局响应处理器。
 *
 * @author oo w
 * @version 1.0
 * @since 2023/8/16 21:02
 */
@Slf4j
@RestControllerAdvice
public class ResponseBodyHandler implements ResponseBodyAdvice<Object> {

    /**
     * Whether this component supports the given controller method return type
     * and the selected {@code HttpMessageConverter} type.
     *
     * @param returnType    the return type
     * @param converterType the selected converter type
     * @return {@code true} if {@link #beforeBodyWrite} should be invoked;
     * {@code false} otherwise
     */
    @Override
    public boolean supports(@NotNull MethodParameter returnType,
                            @NotNull Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    /**
     * Invoked after an {@code HttpMessageConverter} is selected and just before
     * its write method is invoked.
     *
     * @param body                  the body to be written
     * @param returnType            the return type of the controller method
     * @param selectedContentType   the content type selected through content negotiation
     * @param selectedConverterType the converter type selected to write to the response
     * @param request               the current request
     * @param response              the current response
     * @return the body that was passed in or a modified (possibly new) instance
     */
    @Override
    public Object beforeBodyWrite(Object body,
                                  @NotNull MethodParameter returnType,
                                  @NotNull MediaType selectedContentType,
                                  @NotNull Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  @NotNull ServerHttpRequest request,
                                  @NotNull ServerHttpResponse response) {

        try {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            InputStream inputStream = servletRequest.getBody();
            String requestBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
            Long start = (Long) servletRequest.getServletRequest().getAttribute(JwtFilter.REQUEST_TIME);
            long end = System.currentTimeMillis();
            log.info(
                    """
                                
                            请求时间：{}
                            请求接口：{}
                            请求方法：{}
                            请求参数：{}
                            响应结果：{}
                            响应时间：{}
                            响应耗时：{}ms
                            """,
                    DateUtil.timestampToLocalDateTime(start).format(DateUtil.DATE_TIME_FORMATTER),
                    request.getURI(),
                    request.getMethod(),
                    JsonUtil.toJsonObject(requestBody),
                    JsonUtil.toJson(body),
                    DateUtil.timestampToLocalDateTime(end).format(DateUtil.DATE_TIME_FORMATTER),
                    end - start
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return body;
    }

}
