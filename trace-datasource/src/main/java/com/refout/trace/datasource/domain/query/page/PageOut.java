package com.refout.trace.datasource.domain.query.page;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * 页面输出记录
 *
 * @param <T>       数据类型
 * @param data      数据集合
 * @param page      当前页码
 * @param size      每页大小
 * @param total     总记录数
 * @param totalPage 总页数
 * @author oo w
 * @version 1.0
 * @since 2023/8/12 10:09
 */
public record PageOut<T>(Collection<T> data, int page, int size, long total, int totalPage) {

    /**
     * 创建页面输出记录
     *
     * @param data      数据集合
     * @param page      当前页码
     * @param size      每页大小
     * @param total     总记录数
     * @param totalPage 总页数
     * @param <T>       数据类型
     * @return 页面输出记录
     */
    @Contract("_, _, _, _, _ -> new")
    public static <T> @NotNull PageOut<T> of(Collection<T> data, int page, int size, long total, int totalPage) {
        return new PageOut<>(data, page + 1, size, total, totalPage);
    }

}