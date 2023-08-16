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

/**
 * RequestHttpServletRequestWrapper类是一个自定义的HttpServletRequestWrapper，
 * 用于包装请求对象并提供对请求体的访问。
 * <p>
 * 继承自HttpServletRequestWrapper类。
 *
 * @author oo w
 * @version 1.0
 * @since 2023/8/16 21:05
 */
public class RequestHttpServletRequestWrapper extends HttpServletRequestWrapper {

    /**
     * 请求体字节数组
     */
    private final byte[] body;

    /**
     * 构造一个包装给定请求的请求对象。
     *
     * @param request 要包装的请求对象
     * @throws IllegalArgumentException 如果请求为null
     * @throws IOException              如果无法读取请求体
     */
    public RequestHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        body = StreamUtils.copyToByteArray(request.getInputStream());
    }

    /**
     * 重写getReader()方法，返回包装请求对象上的getReader()方法的默认行为。
     *
     * @return BufferedReader对象，用于读取请求体
     */
    @Override
    public BufferedReader getReader() {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    /**
     * 重写getInputStream()方法，返回包装请求对象上的getInputStream()方法的默认行为。
     *
     * @return ServletInputStream对象，用于读取请求体
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