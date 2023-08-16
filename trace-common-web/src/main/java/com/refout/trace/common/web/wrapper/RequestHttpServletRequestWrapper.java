package com.refout.trace.common.web.wrapper;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.springframework.util.StreamUtils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class RequestHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private final byte[] body;

    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request The request to wrap
     * @throws IllegalArgumentException if the request is null
     */
    public RequestHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        body = StreamUtils.copyToByteArray(request.getInputStream());
    }

    /**
     * The default behavior of this method is to return getReader() on the wrapped request object.
     */
    @Override
    public BufferedReader getReader() {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    /**
     * The default behavior of this method is to return getInputStream() on the wrapped request object.
     */
    @Override
    public ServletInputStream getInputStream() {
        ByteArrayInputStream stream = new ByteArrayInputStream(body);
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener listener) {

            }

            @Override
            public int read() {
                return stream.read();
            }
        };
    }

}
