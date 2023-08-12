package com.refout.trace.datasource.domain.page;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * 分页查询输入类，用于指定分页查询的参数。
 *
 * @param page    页码
 * @param size    每页大小
 * @param orderBy 排序
 * @param query   查询条件
 * @param <T>     查询条件的类型
 * @author oo w
 * @version 1.0
 * @since 2023/8/12 23:37
 */
public record PageIn<T>(int page, int size, List<OrderBy> orderBy, T query) {

    /**
     * 获取分页查询的Pageable对象。
     *
     * @return 分页查询的Pageable对象
     */
    @Contract(value = " -> new", pure = true)
    public @NotNull Pageable pageable() {
        if (orderBy == null || orderBy.isEmpty()) {
            return PageRequest.of(page, size);
        }

        List<Sort.Order> list = orderBy.stream()
                .map(it ->
                        new Sort.Order(it.direction(), it.property())
                )
                .toList();

        return PageRequest.of(page, size, Sort.by(list));
    }

}
