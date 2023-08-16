package com.refout.trace.datasource.domain.query.condition;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 范围条件
 * <p>
 * 继承自Column类
 *
 * @author oo w
 * @version 1.0
 * @since 2023/8/16 8:55
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Scope extends Column {

    /**
     * 起始值
     */
    private Object start;

    /**
     * 结束值
     */
    private Object end;

}
