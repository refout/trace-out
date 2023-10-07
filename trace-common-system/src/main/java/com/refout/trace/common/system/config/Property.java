package com.refout.trace.common.system.config;

import com.refout.trace.common.system.domain.Config;
import com.refout.trace.common.system.service.ConfigService;
import com.refout.trace.common.util.ConvertUtil;
import com.refout.trace.common.util.JsonUtil;
import com.refout.trace.common.util.SpringUtil;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * 配置属性
 *
 * @param key    配置属性key
 * @param def    默认值
 * @param belongApp    配置所属应用,common表示所有应用可用
 * @param belongFunc 配置所属功能
 * @param remark 备注
 * @param clazz  配置值所属类型
 * @param <T>    属性值的类型。
 * @author oo w
 * @version 1.0
 * @since 2023/10/7 17:24
 */
public record Property<T>(String key, T def, String belongApp, String belongFunc, String remark, Class<T> clazz) {

    private static final ConfigService service;

    private static final String COMMON = "common";

    static {
        service = SpringUtil.getBean(ConfigService.class);
    }

    /**
     * 创建一个 Property 实例，表示具有指定的键、默认值、所属、备注和类类型。
     *
     * @param key    配置属性的键。
     * @param def    默认值。
     * @param belong 配置所属功能。
     * @param remark 配置属性的备注。
     * @param clazz  配置值的类类型。
     * @param <T>    属性值的类型。
     * @return 新的 Property 实例。
     */
    @NotNull
    @Contract("_, _, _, _, _ -> new")
    public static <T> Property<T> of(String key, T def, String belong, String remark, Class<T> clazz) {
        return new Property<>(key, def, COMMON, belong, remark, clazz);
    }

    /**
     * 创建一个 Property 实例，表示具有指定的键、默认值、应用程序、所属、备注和类类型。
     *
     * @param key    配置属性的键。
     * @param def    默认值。
     * @param app    配置属性的所属应用，common 表示所有应用可用。
     * @param belong 配置属性的所属功能。
     * @param remark 配置属性的备注。
     * @param clazz  配置值的类类型。
     * @param <T>    属性值的类型。
     * @return 新的 Property 实例。
     */
    @NotNull
    @Contract("_, _, _, _, _, _ -> new")
    public static <T> Property<T> of(String key, T def, String app, String belong, String remark, Class<T> clazz) {
        return new Property<>(key, def, app, belong, remark, clazz);
    }

    /**
     * 获取属性值。
     *
     * @return 属性值。
     */
    public T value() {
        T config = service.getConfig(key, belongApp, def, clazz);
        return ConvertUtil.convert(config, def, clazz);
    }

    /**
     * 检查属性是否属于当前应用。
     *
     * @return 如果属性属于当前应用，则返回 true；否则返回 false。
     */
    public boolean belongThisApp() {
        if (COMMON.equalsIgnoreCase(belongApp)) {
            return true;
        }

        return SpringUtil.getApplicationName().equalsIgnoreCase(belongApp);
    }

    /**
     * 将属性转换为 Config 对象。
     *
     * @return 表示属性的 Config 对象。
     */
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
                .setApp(belongApp)
                .setName(key)
                .setValue(value)
                .setBelong(belongFunc)
                .setRemark(remark);
    }

}