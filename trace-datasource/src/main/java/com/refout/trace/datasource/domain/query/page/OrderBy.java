package com.refout.trace.datasource.domain.query.page;

import org.springframework.data.domain.Sort;

/**
 * 排序方式类，用于指定排序的方向和属性。
 *
 * @param direction 排序方向
 * @param property  排序属性
 * @author oo w
 * @version 1.0
 * @since 2023/8/12 23:35
 */
public record OrderBy(Sort.Direction direction, String property) {

}