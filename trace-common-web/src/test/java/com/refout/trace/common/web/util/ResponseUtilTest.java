package com.refout.trace.common.web.util;

import com.refout.trace.common.web.domain.Result;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ResponseUtilTest {

    @Test
    void testResponseWithResult() throws UnsupportedEncodingException {
        // Create a mock HttpServletResponse
        MockHttpServletResponse response = new MockHttpServletResponse();
        // Create a sample Result object
        Result result = new Result(200, "Success");
        // Call the response() method
        ResponseUtil.response(response, result);
        // Assert the response properties
        assertEquals("application/json;charset=UTF-8", response.getContentType());
        assertEquals(200, response.getStatus());
        assertEquals("{\"code\":200,\"msg\":\"Success\",\"data\":null}", response.getContentAsString());
    }

    @Test
    void testResponseWithResult1() throws UnsupportedEncodingException {
        // Create a mock HttpServletResponse
        MockHttpServletResponse response = new MockHttpServletResponse();
        // Create a null Result object
        Result result = new Result(200, "Success");
        // Call the response() method
        ResponseUtil.response(response, result);
        // Assert the response properties
        assertEquals("application/json;charset=UTF-8", response.getContentType());
        assertEquals(200, response.getStatus());
        assertEquals("{\"code\":200,\"msg\":\"Success\",\"data\":null}", response.getContentAsString());
    }

    @Test
    void testResponseWithCodeAndMessage() throws UnsupportedEncodingException {
        // Create a mock HttpServletResponse
        MockHttpServletResponse response = new MockHttpServletResponse();
        // Call the response() method
        ResponseUtil.response(response, 200, "Success");
        // Assert the response properties
        assertEquals("application/json;charset=UTF-8", response.getContentType());
        assertEquals(200, response.getStatus());
        assertEquals("{\"code\":200,\"msg\":\"Success\",\"data\":null}", response.getContentAsString());
    }

    @Test
    void testResponseWithCodeAndMessage1() throws UnsupportedEncodingException {
        // Create a mock HttpServletResponse
        MockHttpServletResponse response = new MockHttpServletResponse();
        // Call the response() method with negative code
        ResponseUtil.response(response, -1, "Error");
        // Assert the response properties
        assertEquals("application/json;charset=UTF-8", response.getContentType());
        assertEquals(200, response.getStatus());
        assertEquals("{\"code\":-1,\"msg\":\"Error\",\"data\":null}", response.getContentAsString());
    }

    @Test
    void testResponseWithHttpStatusAndMessage2() throws UnsupportedEncodingException {
        // Create a mock HttpServletResponse
        MockHttpServletResponse response = new MockHttpServletResponse();
        // Call the response() method
        ResponseUtil.response(response, HttpStatus.OK, "Success");
        // Assert the response properties
        assertEquals("application/json;charset=UTF-8", response.getContentType());
        assertEquals(200, response.getStatus());
        assertEquals("{\"code\":200,\"msg\":\"Success\",\"data\":null}", response.getContentAsString());
    }

    @Test
    void testResponseWithHttpStatusAndMessage() throws UnsupportedEncodingException {
        // Create a mock HttpServletResponse
        MockHttpServletResponse response = new MockHttpServletResponse();
        // Call the response() method with null HttpStatus
        ResponseUtil.response(response, 1, "Error");
        // Assert the response properties
        assertEquals("application/json;charset=UTF-8", response.getContentType());
        assertEquals(200, response.getStatus());
        assertEquals("{\"code\":1,\"msg\":\"Error\",\"data\":null}", response.getContentAsString());
    }

}