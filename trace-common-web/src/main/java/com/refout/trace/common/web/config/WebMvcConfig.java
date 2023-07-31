package com.refout.trace.common.web.config;

import com.refout.trace.common.web.interceptor.AuthenticatedInterceptor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * 拦截器配置
 *
 * @author oo w
 * @version 1.0
 * @since 2023/7/31 15:11
 */
@EnableWebMvc
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 不需要进行JWT验证的路径列表
     */
    @Value("${trace.security.no-filter:}")
    private List<String> noFilter;

    @Override
    public void addInterceptors(@NotNull InterceptorRegistry registry) {
        registry.addInterceptor(new AuthenticatedInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(noFilter)
                .order(0);
    }

}