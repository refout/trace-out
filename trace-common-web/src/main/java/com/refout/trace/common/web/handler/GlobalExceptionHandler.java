package com.refout.trace.common.web.handler;

import com.refout.trace.common.exception.SystemException;
import com.refout.trace.common.web.domain.Result;
import com.refout.trace.common.web.exception.AuthenticationException;
import com.refout.trace.common.web.exception.AuthorizationException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
/**
 * 全局异常处理
 *
 * @author oo w
 * @version 1.0
 * @since 2023/8/1 11:57
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 不支持的请求方法
     * <p>
     * 处理{@link HttpRequestMethodNotSupportedException}异常
     *
     * @param e       {@link HttpRequestMethodNotSupportedException}异常
     * @param request {@link HttpServletRequest}请求
     * @return {@link Result}响应结果
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e,
                                                      HttpServletRequest request) {
        printLog(e, request);
        return Result.fault(HttpStatus.METHOD_NOT_ALLOWED.value(), "不支持的请求方法");
    }

    /**
     * 处理{@link AuthorizationException}异常
     *
     * @param e       {@link AuthorizationException}异常
     * @param request {@link HttpServletRequest}请求
     * @return {@link Result}响应结果
     */
    @ExceptionHandler(AuthorizationException.class)
    public Result handleAuthorizationException(@NotNull AuthorizationException e, @NotNull HttpServletRequest request) {
        return handleException(HttpStatus.UNAUTHORIZED, e, request);
    }

    /**
     * 处理{@link AuthenticationException}异常
     *
     * @param e       {@link AuthenticationException}异常
     * @param request {@link HttpServletRequest}请求
     * @return {@link Result}响应结果
     */
    @ExceptionHandler(AuthenticationException.class)
    public Result handleAuthenticationException(@NotNull AuthenticationException e, @NotNull HttpServletRequest request) {
        return handleException(HttpStatus.FORBIDDEN, e, request);
    }

    /**
     * 处理{@link SystemException}异常
     *
     * @param e       {@link SystemException}异常
     * @param request {@link HttpServletRequest}请求
     * @return {@link Result}响应结果
     */
    @ExceptionHandler(SystemException.class)
    private @NotNull Result handleSystemException(@NotNull SystemException e, @NotNull HttpServletRequest request) {
        return handleException(HttpStatus.INTERNAL_SERVER_ERROR, e, request);
    }

    /**
     * 处理{@link Exception}异常
     *
     * @param e       {@link Exception}异常
     * @param request {@link HttpServletRequest}请求
     * @return {@link Result}响应结果
     */
    @ExceptionHandler(Exception.class)
    private @NotNull Result handleException(@NotNull Exception e, @NotNull HttpServletRequest request) {
        printLog(e, request);
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return Result.fault(status.value(), status.getReasonPhrase());
    }

    /**
     * 处理异常
     *
     * @param httpStatus {@link HttpStatus}响应状态码
     * @param e          异常
     * @param request    {@link HttpServletRequest}请求
     * @return {@link Result}响应结果
     */
    private @NotNull Result handleException(@NotNull HttpStatus httpStatus,
                                            @NotNull Exception e,
                                            @NotNull HttpServletRequest request) {
        printLog(e, request);
        return Result.fault(httpStatus.value(), e.getMessage());
    }

    /**
     * 打印日志
     *
     * @param e       异常
     * @param request {@link HttpServletRequest}请求
     */
    private void printLog(@NotNull Exception e, @NotNull HttpServletRequest request) {
        log.error("请求地址：{}，错误原因：{}", request.getRequestURL(), e.getMessage(), e);
    }

}