package com.refout.trace.common.web.util;

import com.refout.trace.common.web.util.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import org.apache.catalina.connector.RequestFacade;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

public class JwtUtilTest {

    @Test
    public void testCreateTokenWithValidJti() {
        String token = JwtUtil.createToken("12345");
        Assertions.assertNotNull(token);
        Assertions.assertTrue(token.length() > 0);
    }

    @Test
    public void testCreateTokenWithNullJti() {
        String token = JwtUtil.createToken(null);
        Assertions.assertNotNull(token);
        Assertions.assertTrue(token.length() > 0);
    }

    @Test
    public void testCreateTokenWithValidClaimsAndJti() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("name", "John Doe");
        claims.put("role", "admin");
        String token = JwtUtil.createToken(claims, "12345");
        Assertions.assertNotNull(token);
        Assertions.assertTrue(token.length() > 0);
    }

    @Test
    public void testCreateTokenWithNullClaimsAndValidJti() {
        String token = JwtUtil.createToken(null, "12345");
        Assertions.assertNotNull(token);
        Assertions.assertTrue(token.length() > 0);
    }

    @Test
    public void testParseTokenWithValidToken() {
        String token = JwtUtil.createToken("12345");
        Assertions.assertNotNull(token);
        Claims claims = JwtUtil.parseToken(token);
        Assertions.assertNotNull(claims);
        Assertions.assertEquals("12345", claims.getId());
    }

    @Test
    public void testParseTokenWithInvalidToken() {
        String token = "invalid_token";
        Claims claims = JwtUtil.parseToken(token);
        Assertions.assertNull(claims);
    }

    @Test
    public void testGetTokenWithValidAuthorizationHeader() {
        RequestFacade request = Mockito.mock(RequestFacade.class);
        Mockito.when(request.getHeader(JwtUtil.AUTHORIZATION)).thenReturn("Bearer token123");
        String token = JwtUtil.getToken(request);
        Assertions.assertEquals("token123", token);
    }

    @Test
    public void testGetTokenWithNullAuthorizationHeader() {
        RequestFacade request = Mockito.mock(RequestFacade.class);
        Mockito.when(request.getHeader(JwtUtil.AUTHORIZATION)).thenReturn(null);
        String token = JwtUtil.getToken(request);
        Assertions.assertNull(token);
    }

    @Test
    public void testGetTokenWithInvalidAuthorizationHeader() {
        RequestFacade request = Mockito.mock(RequestFacade.class);
        Mockito.when(request.getHeader(JwtUtil.AUTHORIZATION)).thenReturn("InvalidHeader");
        String token = JwtUtil.getToken(request);
        Assertions.assertNull(token);
    }

}