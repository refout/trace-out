package com.refout.trace.common.web.filter;

import com.refout.trace.common.system.domain.authenticated.Authenticated;
import com.refout.trace.common.web.constant.AuthCacheKey;
import com.refout.trace.common.web.util.ResponseUtil;
import com.refout.trace.common.web.util.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.RequestFacade;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
@WebFilter(filterName = "jwtFilter", urlPatterns = "/*")
public class JwtFilter implements Filter {

    /**
     * 不需要进行JWT验证的路径列表
     */
    @Value("${trace.security.no-filter:}")
    private List<String> noFilter;

    @Resource
    private RedisTemplate<String, Authenticated> redisTemplate;

    /**
     * The <code>doFilter</code> method of the Filter is called by the container each time a request/response pair is
     * passed through the chain due to a client request for a resource at the end of the chain. The FilterChain passed
     * in to this method allows the Filter to pass on the request and response to the next entity in the chain.
     * <p>
     * A typical implementation of this method would follow the following pattern:- <br>
     * 1. Examine the request<br>
     * 2. Optionally wrap the request object with a custom implementation to filter content or headers for input
     * filtering <br>
     * 3. Optionally wrap the response object with a custom implementation to filter content or headers for output
     * filtering <br>
     * 4. a) <strong>Either</strong> invoke the next entity in the chain using the FilterChain object
     * (<code>chain.doFilter()</code>), <br>
     * 4. b) <strong>or</strong> not pass on the request/response pair to the next entity in the filter chain to block
     * the request processing<br>
     * 5. Directly set headers on the response after invocation of the next entity in the filter chain.
     *
     * @param request  The request to process
     * @param response The response associated with the request
     * @param chain    Provides access to the next filter in the chain for this filter to pass the request and response
     *                 to for further processing
     * @throws IOException      if an I/O error occurs during this filter's processing of the request
     * @throws ServletException if the processing fails for any other reason
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        RequestFacade requestFacade = (RequestFacade) request;
        String servletPath = requestFacade.getServletPath();
        if (noFilter.contains(servletPath)) {
            chain.doFilter(request, response);
            return;
        }

        // 从请求头中获取JWT令牌
        String token = JwtUtil.getToken(requestFacade);
        // 如果JWT令牌为空或为空字符串，则返回401未授权状态码
        if (token == null) {
            log.info("{} 未携带token，禁止访问", requestFacade.getRequestURL());
            ResponseUtil.response((HttpServletResponse) response, HttpStatus.UNAUTHORIZED, "无权访问");
            return;
        }

        // 从JWT令牌中解析出Claims对象
        Claims claims = JwtUtil.parseToken(token);
        if (claims == null) {
            log.info("token解析失败：{}，禁止访问", token);
            ResponseUtil.response((HttpServletResponse) response, HttpStatus.UNAUTHORIZED, "无权访问");
            return;
        }

        String id = claims.getId();
        String userKey = AuthCacheKey.userKey(id);
        Boolean hasKey = redisTemplate.hasKey(userKey);
        // 登录口令已过期
        if (hasKey == null || !hasKey) {
            log.info("缓存中无toke：{}，禁止访问", token);
            ResponseUtil.response((HttpServletResponse) response, HttpStatus.UNAUTHORIZED, "登录口令已过期，无权访问");
            return;
        }
        Authenticated authenticated = redisTemplate.boundValueOps(userKey).get();
        if (authenticated == null) {
            log.info("获取到的缓存为空：{}，禁止访问", token);
            ResponseUtil.response((HttpServletResponse) response, HttpStatus.UNAUTHORIZED, "登录口令已过期，无权访问");
            return;
        }

        // 管理员
        if (authenticated.user().isAdmin()) {
            chain.doFilter(request, response);
            return;
        }

        Set<String> permissions = authenticated.permissions();
        if (!permissions.contains(servletPath)) {
            log.info("该用户未分配{}权限，禁止访问", servletPath);
            ResponseUtil.response((HttpServletResponse) response, HttpStatus.UNAUTHORIZED, "无该功能权限，无权访问");
            return;
        }
        chain.doFilter(request, response);
    }

}
