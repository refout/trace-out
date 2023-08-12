package com.refout.trace.common.system.service.impl;

import com.refout.trace.common.system.domain.Config;
import com.refout.trace.common.system.repository.ConfigRepository;
import com.refout.trace.common.system.service.ConfigService;
import com.refout.trace.common.util.ConvertUtil;
import com.refout.trace.common.util.StrUtil;
import com.refout.trace.datasource.repository.BaseRepository;
import com.refout.trace.redis.config.key.ConfigCacheKey;
import jakarta.annotation.Resource;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * 配置服务实现，用于获取配置信息。
 *
 * @author oo w
 * @version 1.0
 * @since 2023/8/10 17:14
 */
@Service
public class ConfigServiceImpl implements ConfigService {

    /**
     * 应用名称
     */
    @Value("${spring.application.name}")
    private String applicationName;

    /**
     * 配置仓库
     */
    @Resource
    private ConfigRepository configRepository;

    @Override
    public BaseRepository<Config, Long> repository() {
        return configRepository;
    }

    /**
     * 根据配置名称获取配置值。
     *
     * @param name 配置名称
     * @return 配置值
     */
    @Cacheable(
            cacheNames = ConfigCacheKey.PREFIX,
            keyGenerator = "ConfigCacheKey",
            sync = true
    )
    @Override
    public String getConfig(String name) {
        return configRepository.findValueByNameAndAppWithCommon(name, applicationName);
    }

    /**
     * 根据配置名称获取配置值，如果配置不存在则返回默认值。
     *
     * @param name 配置名称
     * @param def  默认值
     * @return 配置值
     */
    @Cacheable(
            cacheNames = ConfigCacheKey.PREFIX,
            keyGenerator = "ConfigCacheKey",
            sync = true
    )
    @Override
    public String getConfig(String name, String def) {
        String value = configRepository.findValueByNameAndAppWithCommon(name, applicationName);
        if (!StrUtil.hasText(value)) {
            return def;
        }
        return value;
    }

    /**
     * 根据配置名称获取配置值，并将其转换为指定类型。
     *
     * @param name  配置名称
     * @param clazz 目标类型
     * @param <T>   目标类型的泛型
     * @return 配置值
     */
    @Cacheable(
            cacheNames = ConfigCacheKey.PREFIX,
            keyGenerator = "ConfigCacheKey",
            sync = true
    )
    @Override
    public <T> T getConfig(String name, Class<T> clazz) {
        return getConfig(name, applicationName, null, clazz);
    }

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
    @Cacheable(
            cacheNames = ConfigCacheKey.PREFIX,
            keyGenerator = "ConfigCacheKey",
            sync = true
    )
    @Override
    @NotNull
    public <T> T getConfig(String name, String applicationName, T def, Class<T> clazz) {
        String value = configRepository.findValueByNameAndAppWithCommon(name, applicationName);
        return ConvertUtil.convert(value, def, clazz);
    }

    /**
     * 配置是否存在
     *
     * @param name 配置名称
     * @return 是否存在
     */
    @Override
    public boolean exist(String name) {
        return configRepository.exist(name, applicationName) > 0;
    }

}
