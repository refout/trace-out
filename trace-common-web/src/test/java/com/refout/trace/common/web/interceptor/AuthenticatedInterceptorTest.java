package com.refout.trace.common.web.interceptor;

import com.refout.trace.common.system.domain.authenticated.Authenticated;
import com.refout.trace.common.web.context.AuthenticatedContextHolder;
import com.refout.trace.common.web.filter.JwtFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.jupiter.api.Assertions.*;

public class AuthenticatedInterceptorTest {

    private AuthenticatedInterceptor interceptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        interceptor = new AuthenticatedInterceptor();
    }

    @Test
    void testPreHandleWithValidAuthenticated() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        Authenticated authenticated = new Authenticated("token", null, null, null, null, null, null, null);
        request.setAttribute(JwtFilter.CURRENT_USER, authenticated);
        assertTrue(interceptor.preHandle(request, response, new Object()));
        assertEquals(authenticated, AuthenticatedContextHolder.getContext());
        assertNull(request.getAttribute(JwtFilter.CURRENT_USER));
    }

    @Test
    void testPreHandleWithInvalidAuthenticated() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.setAttribute(JwtFilter.CURRENT_USER, "invalid");
        assertTrue(interceptor.preHandle(request, response, new Object()));
        assertNull(AuthenticatedContextHolder.getContext());
        assertNull(request.getAttribute(JwtFilter.CURRENT_USER));
    }

    @Test
    void testAfterCompletion() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        AuthenticatedContextHolder.setContext(new Authenticated("token", null, null, null, null, null, null, null));
        interceptor.afterCompletion(request, response, new Object(), null);
        assertNull(AuthenticatedContextHolder.getContext());
    }

}