package com.refout.trace.datasource.enums;

/**
 * 查询函数枚举，用于指定查询的函数类型。
 *
 * @author oo w
 * @version 1.0
 * @since 2023/8/12 23:41
 */
public enum QueryFunc {

    /**
     * 等于查询
     */
    EQ,

    /**
     * 不等于查询
     */
    NOT_EQ,

    /**
     * 模糊查询
     */
    LIKE,

    /**
     * 左模糊查询
     */
    LEFT_LIKE,

    /**
     * 右模糊查询
     */
    RIGHT_LIKE,

    /**
     * 不包含查询
     */
    NOT_LIKE

}