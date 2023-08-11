
package com.refout.trace.common.util;

import org.jetbrains.annotations.NotNull;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 用于获取Spring容器中的Bean和环境信息的工具类
 *
 * @author oo w
 * @version 1.0
 * @since 2023/7/31 11:57
 */
@SuppressWarnings({"UnusedReturnValue"})
@Component
public final class SpringUtil implements BeanFactoryPostProcessor, ApplicationContextAware {

    /**
     * Spring Bean工厂
     */
    private static ConfigurableListableBeanFactory beanFactory;

    /**
     * Spring 应用上下文
     */
    private static ApplicationContext applicationContext;

    /**
     * BeanFactoryPostProcessor接口方法，用于在BeanFactory实例化之后修改其属性值
     *
     * @param beanFactory 可配置的BeanFactory实例
     * @throws BeansException 如果无法修改属性值
     */
    @Override
    public void postProcessBeanFactory(@NotNull ConfigurableListableBeanFactory beanFactory)
            throws BeansException {
        SpringUtil.beanFactory = beanFactory;
    }

    /**
     * ApplicationContextAware接口方法，用于获取ApplicationContext实例
     *
     * @param applicationContext Spring 应用上下文
     * @throws BeansException 如果无法获取ApplicationContext实例
     */
    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext)
            throws BeansException {
        SpringUtil.applicationContext = applicationContext;
    }

    /**
     * 根据Bean名称获取Bean实例
     *
     * @param name Bean名称
     * @return Bean实例
     * @throws BeansException 如果无法获取Bean实例
     */
    @SuppressWarnings({"unchecked"})
    public static <T> @NotNull T getBean(String name) throws BeansException {
        return (T) beanFactory.getBean(name);
    }

    /**
     * 根据Bean类型获取Bean实例
     *
     * @param clazz Bean类型
     * @return Bean实例
     * @throws BeansException 如果无法获取Bean实例
     */
    public static <T> @NotNull T getBean(Class<T> clazz) throws BeansException {
        return beanFactory.getBean(clazz);
    }

    /**
     * 判断是否存在指定名称的Bean
     *
     * @param name Bean名称
     * @return 如果存在指定名称的Bean，则返回true；否则返回false
     */
    @SuppressWarnings({"UnusedReturnValue"})
    public static boolean containsBean(String name) {
        return beanFactory.containsBean(name);
    }

    /**
     * 判断指定名称的Bean是否为单例
     *
     * @param name Bean名称
     * @return 如果指定名称的Bean为单例，则返回true；否则返回false
     * @throws NoSuchBeanDefinitionException 如果无法获取Bean定义信息
     */
    public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
        return beanFactory.isSingleton(name);
    }

    /**
     * 获取指定名称的Bean类型
     *
     * @param name Bean名称
     * @return Bean类型
     * @throws NoSuchBeanDefinitionException 如果无法获取Bean定义信息
     */
    public static Class<?> getType(String name) throws NoSuchBeanDefinitionException {
        return beanFactory.getType(name);
    }

    /**
     * 获取指定名称的Bean的所有别名
     *
     * @param name Bean名称
     * @return Bean的所有别名
     * @throws NoSuchBeanDefinitionException 如果无法获取Bean定义信息
     */
    public static String @NotNull [] getAliases(String name) throws NoSuchBeanDefinitionException {
        return beanFactory.getAliases(name);
    }

    /**
     * 获取指定对象的AOP代理对象
     *
     * @param invoker 指定对象
     * @return AOP代理对象
     */
    @SuppressWarnings("unchecked")
    public static <T> @NotNull T getAopProxy(T invoker) {
        return (T) AopContext.currentProxy();
    }

    /**
     * 获取当前激活的Spring配置文件中的所有Profile名称
     *
     * @return 所有激活的Profile名称
     */
    public static String @NotNull [] getActiveProfiles() {
        return applicationContext.getEnvironment().getActiveProfiles();
    }

    /**
     * 获取指定key的配置属性值
     *
     * @param key 配置属性的key
     * @return 配置属性的值
     */
    public static @NotNull String getRequiredProperty(String key) {
        return applicationContext.getEnvironment().getRequiredProperty(key);
    }

    public static @NotNull String getApplicationName() {
        return getRequiredProperty("spring.application.name");
    }

}