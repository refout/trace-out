package com.refout.trace.common.system.service;

import com.refout.trace.common.system.domain.Config;
import com.refout.trace.datasource.service.CrudService;

/**
 * 配置服务接口，用于获取配置信息。
 *
 * @author oo w
 * @version 1.0
 * @since 2023/8/10 17:14
 */
public interface ConfigService extends CrudService<Config, Long> {

    /**
     * 根据配置名称获取配置值。
     *
     * @param name 配置名称
     * @return 配置值
     */
    String getConfig(String name);

    /**
     * 根据配置名称获取配置值，如果配置不存在则返回默认值。
     *
     * @param name 配置名称
     * @param def  默认值
     * @return 配置值
     */
    String getConfig(String name, String def);

    /**
     * 根据配置名称获取配置值，并将其转换为指定类型。
     *
     * @param name  配置名称
     * @param clazz 目标类型
     * @param <T>   目标类型的泛型
     * @return 配置值
     */
    <T> T getConfig(String name, Class<T> clazz);

    /**
     * 根据配置名称获取配置值，并将其转换为指定类型，如果配置不存在则返回默认值。
     *
     * @param name            配置名称
     * @param applicationName 应用名称
     * @param def             默认值
     * @param clazz           目标类型
     * @param <T>             目标类型的泛型
     * @return 配置值
     */
    <T> T getConfig(String name, String applicationName, T def, Class<T> clazz);

    /**
     * 配置是否存在
     *
     * @param name 配置名称
     * @return 是否存在
     */
    boolean exist(String name);

}
