package com.refout.trace.common.system.config;

import com.refout.trace.common.system.service.ConfigService;
import com.refout.trace.common.util.ReflectionUtil;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 配置检查类，用于检查系统配置是否完整。
 *
 * @author oo w
 * @version 1.0
 * @since 2023/10/7 17:19
 */
@Slf4j
@Component
public class ConfigCheck {

    /**
     * 应用程序上下文。
     */
    @Resource
    private ApplicationContext applicationContext;

    /**
     * 配置服务。
     */
    @Resource
    private ConfigService service;

    /**
     * 在容器初始化后执行配置检查。
     * 检查所有AbstractConfig类型的Bean中的字段，如果字段对应的配置项不存在，则保存默认配置。
     */
    @PostConstruct
    private void configCheck() {

        // 获取所有 AbstractConfig 类型的 Bean
        Map<String, AbstractConfig> map = applicationContext.getBeansOfType(AbstractConfig.class);

        // 获取所有字段上标注了 @Property 注解的字段
        List<Field> fields = map.values().stream()
                .flatMap(bean ->
                        Arrays.stream(ReflectionUtil.getFields(bean.getClass(), Property.class))
                )
                .toList();

        // 遍历字段进行配置检查
        for (Field field : fields) {
            try {
                // 获取字段的值
                Property<?> value = (Property<?>) ReflectionUtil.getField(field, this);
                if (value == null) {
                    continue;
                }
                if (!value.belongThisApp()) {
                    continue;
                }
                if (service.exist(value.key())) {
                    continue;
                }
                // 配置项不存在，保存默认配置
                log.error("缺少系统配置：{}，保存默认配置：{}", value.key(), value.def());
                service.save(value.to());
            } catch (Exception e) {
                log.error("系统配置检查失败，获取配置声明字段失败", e);
            }
        }
    }

}
