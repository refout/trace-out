package com.refout.trace.common.system.config;

import com.refout.trace.common.system.domain.Config;
import com.refout.trace.common.system.service.ConfigService;
import com.refout.trace.common.util.ConvertUtil;
import com.refout.trace.common.util.JsonUtil;
import com.refout.trace.common.util.SpringUtil;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public record Property<T>(String key, T def, String app, String belong, String remark, Class<T> clazz) {

    private static final ConfigService service;

    private static final String COMMON = "common";

    static {
        service = SpringUtil.getBean(ConfigService.class);
    }

    @NotNull
    @Contract("_, _, _, _, _ -> new")
    public static <T> Property<T> of(String key, T def, String belong, String remark, Class<T> clazz) {
        return new Property<>(key, def, COMMON, belong, remark, clazz);
    }

    @NotNull
    @Contract("_, _, _, _, _, _ -> new")
    public static <T> Property<T> of(String key, T def, String app, String belong, String remark, Class<T> clazz) {
        return new Property<>(key, def, app, belong, remark, clazz);
    }

    public T value() {
        T config = service.getConfig(key, app, def, clazz);
        return ConvertUtil.convert(config, def, clazz);
    }

    public boolean belongThis() {
        if (COMMON.equalsIgnoreCase(app)) {
            return true;
        }

        return SpringUtil.getApplicationName().equalsIgnoreCase(app);
    }

    public Config to() {
        Object value;
        if (def instanceof Number) {
            value = def;
        } else if (def instanceof String defStr && !JsonUtil.isJson(defStr)) {
            value = def;
        } else {
            value = JsonUtil.toJson(def);
        }
        return new Config()
                .setApp(app)
                .setName(key)
                .setValue(value)
                .setBelong(belong)
                .setRemark(remark);
    }

}