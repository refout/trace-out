package com.refout.trace.common.web.handler;

import com.refout.trace.common.exception.SystemException;
import com.refout.trace.common.web.domain.Result;
import com.refout.trace.common.web.exception.AuthenticationException;
import com.refout.trace.common.web.exception.AuthorizationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.HttpRequestMethodNotSupportedException;

public class GlobalExceptionHandlerTest {

    @Test
    public void testHandleHttpRequestMethodNotSupported() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        MockHttpServletRequest request = new MockHttpServletRequest();
        HttpRequestMethodNotSupportedException exception = new HttpRequestMethodNotSupportedException("GET");
        Result result = handler.handleHttpRequestMethodNotSupported(exception, request);
        Assertions.assertEquals(HttpStatus.METHOD_NOT_ALLOWED.value(), result.code());
        Assertions.assertEquals("不支持的请求方法", result.msg());
    }

    @Test
    public void testHandleAuthorizationException() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        MockHttpServletRequest request = new MockHttpServletRequest();
        AuthorizationException exception = new AuthorizationException("Authorization failed");
        Result result = handler.handleAuthorizationException(exception, request);
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED.value(), result.code());
        Assertions.assertEquals("Authorization failed", result.msg());
    }

    @Test
    public void testHandleAuthenticationException() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        MockHttpServletRequest request = new MockHttpServletRequest();
        AuthenticationException exception = new AuthenticationException("Authentication failed");
        Result result = handler.handleAuthenticationException(exception, request);
        Assertions.assertEquals(HttpStatus.FORBIDDEN.value(), result.code());
        Assertions.assertEquals("Authentication failed", result.msg());
    }

    @Test
    public void testHandleSystemException() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        MockHttpServletRequest request = new MockHttpServletRequest();
        SystemException exception = new SystemException("System error");
        Result result = ReflectionTestUtils.invokeMethod(handler, "handleSystemException", exception, request);
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), result.code());
        Assertions.assertEquals("System error", result.msg());
    }

}