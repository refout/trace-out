package com.refout.trace.common.web.filter;

import com.refout.trace.common.web.config.CommonConfig;
import com.refout.trace.common.web.constant.AuthCacheKey;
import com.refout.trace.common.web.domain.Authenticated;
import com.refout.trace.common.web.util.ResponseUtil;
import com.refout.trace.common.web.util.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.RequestFacade;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * JwtFilter类实现了Filter接口，用于JWT验证。
 * 使用@Slf4j注解为该类提供日志记录功能。
 * 使用@Component注解将该类标记为Spring组件。
 * 使用@Order注解设置该类的加载顺序。
 * 使用@WebFilter注解定义过滤器的名称和过滤的URL模式。
 *
 * @author oo w
 * @version 1.0
 * @since 2023/7/31 12:39
 */
@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
@WebFilter(filterName = "jwtFilter", urlPatterns = "/*")
public class JwtFilter extends OncePerRequestFilter {

    public static final String CURRENT_USER = "CURRENT_USER_AUTHENTICATED";

    /**
     * 不需要进行JWT验证的路径列表
     */
    @Value("${trace.security.no-filter:[]}")
    private List<String> noFilter;

    /**
     * Redis模板，用于操作Redis数据库
     */
    @Resource
    private RedisTemplate<String, Authenticated> redisTemplateAuthenticated;

    /**
     * doFilter方法是Filter接口的核心方法，用于处理每次请求/响应对。
     * 它的主要任务是检查请求，对请求和响应进行必要处理，然后将请求和响应传递给过滤器链中的下一个实体。
     *
     * @param request     需要处理的请求
     * @param response    与请求关联的响应
     * @param filterChain 提供对过滤器链中此过滤器的下一个过滤器的访问，以便此过滤器传递请求和响应以进行进一步处理
     * @throws IOException      如果在此过滤器处理请求期间发生I/O错误
     * @throws ServletException 如果处理由于其他原因失败
     */
    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws IOException, ServletException {
        RequestFacade requestFacade = (RequestFacade) request;
        String servletPath = requestFacade.getServletPath();
        if (noFilter.contains(servletPath)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 从请求头中获取JWT令牌
        String token = JwtUtil.getToken(requestFacade);
        // 如果JWT令牌为空或为空字符串，则返回401未授权状态码
        if (token == null) {
            log.info("{} 未携带token，禁止访问", requestFacade.getRequestURL());
            ResponseUtil.response(response, HttpStatus.UNAUTHORIZED, "无权访问");
            return;
        }

        // 从JWT令牌中解析出Claims对象
        Claims claims = JwtUtil.parseToken(token);
        if (claims == null) {
            log.info("token解析失败：{}，禁止访问", token);
            ResponseUtil.response(response, HttpStatus.UNAUTHORIZED, "无权访问");
            return;
        }

        String id = claims.getId();
        String userKey = AuthCacheKey.userKey(id);
        Boolean hasKey = redisTemplateAuthenticated.hasKey(userKey);
        // 登录口令已过期
        if (hasKey == null || !hasKey) {
            log.info("缓存中无toke：{}，禁止访问", token);
            ResponseUtil.response(response, HttpStatus.UNAUTHORIZED, "登录口令已过期，无权访问");
            return;
        }
        Authenticated authenticated = redisTemplateAuthenticated.boundValueOps(userKey).get();
        if (authenticated == null) {
            log.info("获取到的缓存为空：{}，禁止访问", token);
            ResponseUtil.response(response, HttpStatus.UNAUTHORIZED, "登录口令已过期，无权访问");
            return;
        }

        // 管理员
        if (authenticated.getUser().isAdmin()) {
            refreshToken(userKey);
            request.setAttribute(CURRENT_USER, authenticated);
            filterChain.doFilter(request, response);
            return;
        }

        Set<String> permissions = authenticated.getPermissions();
        if (!permissions.contains(servletPath)) {
            log.info("该用户未分配{}权限，禁止访问", servletPath);
            ResponseUtil.response(response, HttpStatus.UNAUTHORIZED, "无该功能权限，无权访问");
            return;
        }
        refreshToken(userKey);
        request.setAttribute(CURRENT_USER, authenticated);
        filterChain.doFilter(request, response);
    }

    /**
     * token过期时间刷新
     *
     * @param userKey key
     */
    private void refreshToken(final String userKey) {
        redisTemplateAuthenticated.expire(userKey, CommonConfig.TOKEN_EXPIRATION_SECOND.value(), TimeUnit.SECONDS);
    }

}
