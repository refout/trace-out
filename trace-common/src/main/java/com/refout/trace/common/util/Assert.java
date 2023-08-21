package com.refout.trace.common.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 断言工具类，用于在条件不满足时抛出异常。
 * 可以使用消息函数或运行时异常类来创建实例。
 *
 * @author oo w
 * @version 1.0
 * @since 2023/8/18 13:36
 */
public class Assert {

    /**
     * 断言表达式为真，否则抛出异常。
     *
     * @param exceptionInstance 抛出的异常实例
     * @param expression 表达式
     * @param message    异常消息
     */
    public static void isTrue(@NotNull Function<String, ? extends RuntimeException> exceptionInstance,
                              Boolean expression, String message) {
        if (!expression) {
            throw exceptionInstance.apply(message);
        }
    }

    /**
     * 断言表达式为真，否则抛出异常。
     *
     * @param exceptionInstance 抛出的异常实例
     * @param expression        表达式
     * @param messageSupplier   异常消息的供应商
     */
    public static void isTrue(@NotNull Function<String, ? extends RuntimeException> exceptionInstance,
                              Boolean expression, Supplier<String> messageSupplier) {
        isTrue(exceptionInstance, expression, nullSafeGet(messageSupplier));
    }

    /**
     * 检查对象是否为null，如果不为null则抛出异常。
     *
     * @param exceptionInstance 抛出的异常实例
     * @param object            对象
     * @param message           异常消息
     */
    public static void isNull(@NotNull Function<String, ? extends RuntimeException> exceptionInstance,
                              @Nullable Object object, String message) {
        if (object != null) {
            throw exceptionInstance.apply(message);
        }
    }

    /**
     * 检查对象是否为null，如果不为null则抛出异常。
     *
     * @param exceptionInstance 抛出的异常实例
     * @param object            对象
     * @param messageSupplier   异常消息的供应商
     */
    public static void isNull(@NotNull Function<String, ? extends RuntimeException> exceptionInstance,
                              @Nullable Object object, Supplier<String> messageSupplier) {
        isNull(exceptionInstance, object, nullSafeGet(messageSupplier));
    }

    /**
     * 检查对象是否不为null，如果为null则抛出异常。
     *
     * @param exceptionInstance 抛出的异常实例
     * @param object            对象
     * @param message           异常消息
     */
    public static void notNull(@NotNull Function<String, ? extends RuntimeException> exceptionInstance,
                               @Nullable Object object, String message) {
        if (object == null) {
            throw exceptionInstance.apply(message);
        }
    }

    /**
     * 检查对象是否不为null，如果为null则抛出异常。
     *
     * @param exceptionInstance 抛出的异常实例
     * @param object            对象
     * @param messageSupplier   异常消息的供应商
     */
    public static void notNull(@NotNull Function<String, ? extends RuntimeException> exceptionInstance,
                               @Nullable Object object, Supplier<String> messageSupplier) {
        notNull(exceptionInstance, object, nullSafeGet(messageSupplier));
    }

    /**
     * 检查字符串是否具有长度，如果不具有长度则抛出异常。
     *
     * @param exceptionInstance 抛出的异常实例
     * @param text              字符串
     * @param message           异常消息
     */
    public static void hasLength(@NotNull Function<String, ? extends RuntimeException> exceptionInstance,
                                 @Nullable String text, String message) {
        if (text == null) {
            throw exceptionInstance.apply(message);
        }
        if (!StrUtil.hasLength(text)) {
            throw exceptionInstance.apply(message);
        }
    }

    /**
     * 检查字符串是否具有长度，如果不具有长度则抛出异常。
     *
     * @param exceptionInstance 抛出的异常实例
     * @param text              字符串
     * @param messageSupplier   异常消息的供应商
     */
    public static void hasLength(@NotNull Function<String, ? extends RuntimeException> exceptionInstance,
                                 @Nullable String text, Supplier<String> messageSupplier) {
        hasLength(exceptionInstance, text, nullSafeGet(messageSupplier));
    }

    /**
     * 检查字符串是否具有文本内容，如果不具有文本内容则抛出异常。
     *
     * @param exceptionInstance 抛出的异常实例
     * @param text              字符串
     * @param message           异常消息
     */
    public static void hasText(@NotNull Function<String, ? extends RuntimeException> exceptionInstance,
                               @Nullable String text, String message) {
        if (text == null) {
            throw exceptionInstance.apply(message);
        }
        if (!StrUtil.hasText(text)) {
            throw exceptionInstance.apply(message);
        }
    }

    /**
     * 检查字符串是否具有文本内容，如果不具有文本内容则抛出异常。
     *
     * @param exceptionInstance 抛出的异常实例
     * @param text              字符串
     * @param messageSupplier   异常消息的供应商
     */
    public static void hasText(@NotNull Function<String, ? extends RuntimeException> exceptionInstance,
                               @Nullable String text, Supplier<String> messageSupplier) {
        hasText(exceptionInstance, text, nullSafeGet(messageSupplier));
    }

    /**
     * 检查字符串是否不包含指定的子字符串，如果包含则抛出异常。
     *
     * @param exceptionInstance 抛出的异常实例
     * @param textToSearch      要搜索的字符串
     * @param substring         子字符串
     * @param message           异常消息
     */
    public static void doesNotContain(@NotNull Function<String, ? extends RuntimeException> exceptionInstance,
                                      @Nullable String textToSearch, String substring, String message) {
        if (StrUtil.hasLength(textToSearch) && StrUtil.hasLength(substring) && textToSearch.contains(substring)) {
            throw exceptionInstance.apply(message);
        }
    }

    /**
     * 检查字符串是否不包含指定的子字符串，如果包含则抛出异常。
     *
     * @param exceptionInstance 抛出的异常实例
     * @param textToSearch      要搜索的字符串
     * @param substring         子字符串
     * @param messageSupplier   异常消息的供应商
     */
    public static void doesNotContain(@NotNull Function<String, ? extends RuntimeException> exceptionInstance,
                                      @Nullable String textToSearch, String substring, Supplier<String> messageSupplier) {
        doesNotContain(exceptionInstance, textToSearch, substring, nullSafeGet(messageSupplier));
    }

    /**
     * 检查数组是否为空，如果为空则抛出异常。
     *
     * @param exceptionInstance 抛出的异常实例
     * @param array             数组
     * @param message           异常消息
     */
    public static void notEmpty(@NotNull Function<String, ? extends RuntimeException> exceptionInstance,
                                @Nullable Object[] array, String message) {
        if (ObjectUtils.isEmpty(array)) {
            throw exceptionInstance.apply(message);
        }
    }

    /**
     * 检查数组是否为空，如果为空则抛出异常。
     *
     * @param exceptionInstance 抛出的异常实例
     * @param array             数组
     * @param messageSupplier   异常消息的供应商
     */
    public static void notEmpty(@NotNull Function<String, ? extends RuntimeException> exceptionInstance,
                                @Nullable Object[] array, Supplier<String> messageSupplier) {
        notEmpty(exceptionInstance, array, nullSafeGet(messageSupplier));
    }

    /**
     * 检查数组中是否存在 null 元素，如果存在则抛出异常。
     *
     * @param exceptionInstance 抛出的异常实例
     * @param array             数组
     * @param message           异常消息
     */
    public static void noNullElements(@NotNull Function<String, ? extends RuntimeException> exceptionInstance,
                                      @Nullable Object[] array, String message) {
        if (array != null) {
            for (Object element : array) {
                if (element == null) {
                    throw exceptionInstance.apply(message);
                }
            }
        }
    }

    /**
     * 检查数组中是否存在 null 元素，如果存在则抛出异常。
     *
     * @param exceptionInstance 抛出的异常实例
     * @param array             数组
     * @param messageSupplier   异常消息的供应商
     */
    public static void noNullElements(@NotNull Function<String, ? extends RuntimeException> exceptionInstance,
                                      @Nullable Object[] array, Supplier<String> messageSupplier) {
        noNullElements(exceptionInstance, array, nullSafeGet(messageSupplier));
    }

    /**
     * 检查集合是否为空，如果为空则抛出异常。
     *
     * @param exceptionInstance 抛出的异常实例
     * @param collection        集合
     * @param message           异常消息
     */
    public static void notEmpty(@NotNull Function<String, ? extends RuntimeException> exceptionInstance,
                                @Nullable Collection<?> collection, String message) {
        if (CollectionUtils.isEmpty(collection)) {
            throw exceptionInstance.apply(message);
        }
    }

    /**
     * 检查集合是否为空，如果为空则抛出异常。
     *
     * @param exceptionInstance 抛出的异常实例
     * @param collection        集合
     * @param messageSupplier   异常消息的供应商
     */
    public static void notEmpty(@NotNull Function<String, ? extends RuntimeException> exceptionInstance,
                                @Nullable Collection<?> collection, Supplier<String> messageSupplier) {
        notEmpty(exceptionInstance, collection, nullSafeGet(messageSupplier));
    }

    /**
     * 检查集合中是否存在 null 元素，如果存在则抛出异常。
     *
     * @param exceptionInstance 抛出的异常实例
     * @param collection        集合
     * @param message           异常消息
     */
    public static void noNullElements(@NotNull Function<String, ? extends RuntimeException> exceptionInstance,
                                      @Nullable Collection<?> collection, String message) {
        if (collection != null) {
            for (Object element : collection) {
                if (element == null) {
                    throw exceptionInstance.apply(message);
                }
            }
        }
    }

    /**
     * 检查集合中是否存在 null 元素，如果存在则抛出异常。
     *
     * @param exceptionInstance 抛出的异常实例
     * @param collection        集合
     * @param messageSupplier   异常消息的供应商
     */
    public static void noNullElements(@NotNull Function<String, ? extends RuntimeException> exceptionInstance,
                                      @Nullable Collection<?> collection, Supplier<String> messageSupplier) {
        noNullElements(exceptionInstance, collection, nullSafeGet(messageSupplier));
    }

    /**
     * 检查 Map 是否为空，如果为空则抛出异常。
     *
     * @param exceptionInstance 抛出的异常实例
     * @param map               Map
     * @param message           异常消息
     */
    public static void notEmpty(@NotNull Function<String, ? extends RuntimeException> exceptionInstance,
                                @Nullable Map<?, ?> map, String message) {
        if (CollectionUtils.isEmpty(map)) {
            throw exceptionInstance.apply(message);
        }
    }

    /**
     * 检查 Map 是否为空，如果为空则抛出异常。
     *
     * @param exceptionInstance 抛出的异常实例
     * @param map               Map
     * @param messageSupplier   异常消息的供应商
     */
    public static void notEmpty(@NotNull Function<String, ? extends RuntimeException> exceptionInstance,
                                @Nullable Map<?, ?> map, Supplier<String> messageSupplier) {
        notEmpty(exceptionInstance, map, nullSafeGet(messageSupplier));
    }

    /**
     * 获取非空的消息字符串，如果消息供应商为空则返回null。
     *
     * @param messageSupplier 消息供应商
     * @return 非空的消息字符串或null
     */
    private static String nullSafeGet(@Nullable Supplier<String> messageSupplier) {
        return (messageSupplier != null ? messageSupplier.get() : null);
    }

}
