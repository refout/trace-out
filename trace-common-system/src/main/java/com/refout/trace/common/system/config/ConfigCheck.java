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

@Slf4j
@Component
public class ConfigCheck {

    @Resource
    private ApplicationContext applicationContext;

    @Resource
    private ConfigService service;

    @PostConstruct
    private void configCheck() {

        Map<String, AbstractConfig> map = applicationContext.getBeansOfType(AbstractConfig.class);
        List<Field> fields = map.values().stream()
                .flatMap(bean ->
                        Arrays.stream(ReflectionUtil.getFields(bean.getClass(), Property.class))
                )
                .toList();

        for (Field field : fields) {
            try {
                Property<?> value = (Property<?>) ReflectionUtil.getField(field, this);
                if (value == null) {
                    continue;
                }
                if (!value.belongThis()) {
                    continue;
                }
                if (service.exist(value.key())) {
                    continue;
                }
                log.error("缺少系统配置：{}，保存默认配置：{}", value.key(), value.def());
                service.save(value.to());
            } catch (Exception e) {
                log.error("系统配置检查失败，获取配置声明字段失败", e);
            }
        }
    }

}
