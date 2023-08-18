package com.refout.trace.common.exception;

import com.refout.trace.common.util.StrUtil;
import org.jetbrains.annotations.Nullable;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.Collection;
import java.util.Map;
import java.util.function.Supplier;

/**
 * 抽象异常断言类，用于在条件不满足时抛出异常。
 * 该类提供了一系列的断言方法，用于检查条件并抛出异常。
 * 子类需要实现`exceptionInstance`方法，用于创建具体的异常实例。
 *
 * @author oo w
 * @version 1.0
 * @since 2023/8/18 11:37
 */
public abstract class AbstractExceptionAssert {

    /**
     * 子类需要实现该方法来创建具体的异常实例。
     *
     * @param message 异常消息
     * @return 运行时异常实例
     */
    protected abstract RuntimeException exceptionInstance(String message);

    /**
     * 断言表达式为真，否则抛出异常。
     *
     * @param expression 表达式
     * @param message    异常消息
     */
    public void isTrue(Boolean expression, String message) {
        if (!expression) {
            throw exceptionInstance(message);
        }
    }

    /**
     * 断言表达式为真，否则抛出异常。
     *
     * @param expression      表达式
     * @param messageSupplier 异常消息的供应商
     */
    public void isTrue(Boolean expression, Supplier<String> messageSupplier) {
        isTrue(expression, nullSafeGet(messageSupplier));
    }

    /**
     * 检查对象是否为null，如果不为null则抛出异常。
     *
     * @param object  对象
     * @param message 异常消息
     */
    public void isNull(@Nullable Object object, String message) {
        if (object != null) {
            throw exceptionInstance(message);
        }
    }

    /**
     * 检查对象是否为null，如果不为null则抛出异常。
     *
     * @param object          对象
     * @param messageSupplier 异常消息的供应商
     */
    public void isNull(@Nullable Object object, Supplier<String> messageSupplier) {
        isNull(object, nullSafeGet(messageSupplier));
    }

    /**
     * 检查对象是否不为null，如果为null则抛出异常。
     *
     * @param object  对象
     * @param message 异常消息
     */
    public void notNull(@Nullable Object object, String message) {
        if (object == null) {
            throw exceptionInstance(message);
        }
    }

    /**
     * 检查对象是否不为null，如果为null则抛出异常。
     *
     * @param object          对象
     * @param messageSupplier 异常消息的供应商
     */
    public void notNull(@Nullable Object object, Supplier<String> messageSupplier) {
        notNull(object, nullSafeGet(messageSupplier));
    }

    /**
     * 检查字符串是否具有长度，如果不具有长度则抛出异常。
     *
     * @param text    字符串
     * @param message 异常消息
     */
    public void hasLength(@Nullable String text, String message) {
        if (!StrUtil.hasLength(text)) {
            throw exceptionInstance(message);
        }
    }

    /**
     * 检查字符串是否具有长度，如果不具有长度则抛出异常。
     *
     * @param text            字符串
     * @param messageSupplier 异常消息的供应商
     */
    public void hasLength(@Nullable String text, Supplier<String> messageSupplier) {
        if (!StrUtil.hasLength(text)) {
            throw exceptionInstance(nullSafeGet(messageSupplier));
        }
    }

    /**
     * 检查字符串是否具有文本内容，如果不具有文本内容则抛出异常。
     *
     * @param text    字符串
     * @param message 异常消息
     */
    public void hasText(@Nullable String text, String message) {
        if (!StrUtil.hasText(text)) {
            throw exceptionInstance(message);
        }
    }

    /**
     * 检查字符串是否具有文本内容，如果不具有文本内容则抛出异常。
     *
     * @param text            字符串
     * @param messageSupplier 异常消息的供应商
     */
    public void hasText(@Nullable String text, Supplier<String> messageSupplier) {
        hasText(text, nullSafeGet(messageSupplier));
    }

    /**
     * 检查字符串是否不包含指定的子字符串，如果包含则抛出异常。
     *
     * @param textToSearch 要搜索的字符串
     * @param substring    子字符串
     * @param message      异常消息
     */
    public void doesNotContain(@Nullable String textToSearch, String substring, String message) {
        if (StrUtil.hasLength(textToSearch) && StrUtil.hasLength(substring) &&
                textToSearch.contains(substring)) {
            throw exceptionInstance(message);
        }
    }

    /**
     * 检查字符串是否不包含指定的子字符串，如果包含则抛出异常。
     *
     * @param textToSearch    要搜索的字符串
     * @param substring       子字符串
     * @param messageSupplier 异常消息的供应商
     */
    public void doesNotContain(@Nullable String textToSearch, String substring, Supplier<String> messageSupplier) {
        doesNotContain(textToSearch, substring, nullSafeGet(messageSupplier));
    }

    /**
     * 检查数组是否为空，如果为空则抛出异常。
     *
     * @param array   数组
     * @param message 异常消息
     */
    public void notEmpty(@Nullable Object[] array, String message) {
        if (ObjectUtils.isEmpty(array)) {
            throw exceptionInstance(message);
        }
    }

    /**
     * 检查数组是否为空，如果为空则抛出异常。
     *
     * @param array           数组
     * @param messageSupplier 异常消息的供应商
     */
    public void notEmpty(@Nullable Object[] array, Supplier<String> messageSupplier) {
        notEmpty(array, nullSafeGet(messageSupplier));
    }

    /**
     * 检查数组中是否存在 null 元素，如果存在则抛出异常。
     *
     * @param array   数组
     * @param message 异常消息
     */
    public void noNullElements(@Nullable Object[] array, String message) {
        if (array != null) {
            for (Object element : array) {
                if (element == null) {
                    throw exceptionInstance(message);
                }
            }
        }
    }

    /**
     * 检查数组中是否存在 null 元素，如果存在则抛出异常。
     *
     * @param array           数组
     * @param messageSupplier 异常消息的供应商
     */
    public void noNullElements(@Nullable Object[] array, Supplier<String> messageSupplier) {
        noNullElements(array, nullSafeGet(messageSupplier));
    }

    /**
     * 检查集合是否为空，如果为空则抛出异常。
     *
     * @param collection 集合
     * @param message    异常消息
     */
    public void notEmpty(@Nullable Collection<?> collection, String message) {
        if (CollectionUtils.isEmpty(collection)) {
            throw exceptionInstance(message);
        }
    }

    /**
     * 检查集合是否为空，如果为空则抛出异常。
     *
     * @param collection      集合
     * @param messageSupplier 异常消息的供应商
     */
    public void notEmpty(@Nullable Collection<?> collection, Supplier<String> messageSupplier) {
        notEmpty(collection, nullSafeGet(messageSupplier));
    }

    /**
     * 检查集合中是否存在 null 元素，如果存在则抛出异常。
     *
     * @param collection 集合
     * @param message    异常消息
     */
    public void noNullElements(@Nullable Collection<?> collection, String message) {
        if (collection != null) {
            for (Object element : collection) {
                if (element == null) {
                    throw exceptionInstance(message);
                }
            }
        }
    }

    /**
     * 检查集合中是否存在 null 元素，如果存在则抛出异常。
     *
     * @param collection      集合
     * @param messageSupplier 异常消息的供应商
     */
    public void noNullElements(@Nullable Collection<?> collection, Supplier<String> messageSupplier) {
        noNullElements(collection, nullSafeGet(messageSupplier));
    }

    /**
     * 检查 Map 是否为空，如果为空则抛出异常。
     *
     * @param map     Map
     * @param message 异常消息
     */
    public void notEmpty(@Nullable Map<?, ?> map, String message) {
        if (CollectionUtils.isEmpty(map)) {
            throw exceptionInstance(message);
        }
    }

    /**
     * 检查 Map 是否为空，如果为空则抛出异常。
     *
     * @param map             Map
     * @param messageSupplier 异常消息的供应商
     */
    public void notEmpty(@Nullable Map<?, ?> map, Supplier<String> messageSupplier) {
        notEmpty(map, nullSafeGet(messageSupplier));
    }

    /**
     * 获取非空的消息字符串，如果消息供应商为空则返回null。
     *
     * @param messageSupplier 消息供应商
     * @return 非空的消息字符串或null
     */
    private String nullSafeGet(@Nullable Supplier<String> messageSupplier) {
        return (messageSupplier != null ? messageSupplier.get() : null);
    }

}
