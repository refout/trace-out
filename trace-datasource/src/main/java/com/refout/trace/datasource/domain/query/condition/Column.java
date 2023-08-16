package com.refout.trace.datasource.domain.query.condition;

import lombok.Data;

/**
 * 列
 *
 * @author oo w
 * @version 1.0
 * @since 2023/8/16 8:56
 */
@Data
public abstract class Column {

    /**
     * 列名
     */
    private String columnName;

}
