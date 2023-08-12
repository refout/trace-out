package com.refout.trace.datasource.domain.page;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * TODO
 *
 * @author oo w
 * @version 1.0
 * @since 2023/8/11 22:29
 */

public record PageIn<T>(int page, int size, List<OrderBy> orderBy, T query) {

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


