package com.refout.trace.common.util;

import com.refout.trace.common.exception.AbstractExceptionAssert;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.util.function.Function;

/**
 * 断言工具类，用于在条件不满足时抛出异常。
 * 该类提供了两个静态方法，通过实现 exceptionInstance 方法来创建 AbstractExceptionAssert 的实例。
 * 可以使用消息函数或运行时异常类来创建实例。
 *
 * <p>示例用法：</p>
 * <pre>{@code
 * AbstractExceptionAssert assert1 = Assert.of(message -> new CustomException(message));
 * AbstractExceptionAssert assert2 = Assert.of(CustomException.class);
 * }</pre>
 *
 * <p>注意：子类需要实现 exceptionInstance 方法来创建具体的异常实例。</p>
 *
 * @author oo w
 * @version 1.0
 * @since 2023/8/18 13:36
 */
public class Assert {

    /**
     * 使用消息函数创建 AbstractExceptionAssert 的实例。
     *
     * @param messageFunc 创建带有给定消息的运行时异常的函数
     * @return AbstractExceptionAssert 的实例
     */
    @Contract(value = "_ -> new", pure = true)
    public static @NotNull AbstractExceptionAssert of(Function<String, ? extends RuntimeException> messageFunc) {
        return new AbstractExceptionAssert() {
            /**
             * 子类需要实现该方法来创建具体的异常实例。
             *
             * @param message 异常消息
             * @return 运行时异常实例
             */
            @Override
            protected RuntimeException exceptionInstance(String message) {
                return messageFunc.apply(message);
            }
        };
    }

    /**
     * 使用运行时异常类创建 AbstractExceptionAssert 的实例。
     *
     * @param clazz 运行时异常的类
     * @return AbstractExceptionAssert 的实例
     */
    @Contract(value = "_ -> new", pure = true)
    public static @NotNull AbstractExceptionAssert of(Class<? extends RuntimeException> clazz) {
        return new AbstractExceptionAssert() {
            /**
             * 子类需要实现该方法来创建具体的异常实例。
             *
             * @param message 异常消息
             * @return 运行时异常实例
             */
            @Override
            protected RuntimeException exceptionInstance(String message) {
                try {
                    return clazz.getDeclaredConstructor(String.class).newInstance(message);
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                         NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }

}
