package com.refout.trace.common.web.config;

import com.refout.trace.common.util.JsonUtil;
import com.refout.trace.common.web.interceptor.AuthenticatedInterceptor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
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
    @Value("${trace.security.no-filter:[]}")
    private List<String> noFilter;

    @Override
    public void addInterceptors(@NotNull InterceptorRegistry registry) {
        registry.addInterceptor(new AuthenticatedInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(noFilter)
                .order(0);
    }

    /**
     * Extend or modify the list of converters after it has been, either
     * {@link #configureMessageConverters(List) configured} or initialized with
     * a default list.
     * <p>Note that the order of converter registration is important. Especially
     * in cases where clients accept {@link MediaType#ALL}
     * the converters configured earlier will be preferred.
     *
     * @param converters the list of configured converters to be extended
     * @since 4.1.3
     */
    @Override
    public void extendMessageConverters(@NotNull List<HttpMessageConverter<?>> converters) {
        WebMvcConfigurer.super.extendMessageConverters(converters);
        converters.forEach(it -> {
            // jackson 添加支持LocalDate等的支持，以及Long转换为String
            if (it instanceof AbstractJackson2HttpMessageConverter) {
                ((AbstractJackson2HttpMessageConverter) it).setObjectMapper(JsonUtil.mapper);
            }
        });
    }

}