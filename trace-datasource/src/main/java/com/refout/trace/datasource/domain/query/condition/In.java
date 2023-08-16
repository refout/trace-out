package com.refout.trace.datasource.domain.query.condition;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Collection;

/**
 * 包含条件
 * <p>
 * 继承自Column类
 *
 * @author oo w
 * @version 1.0
 * @since 2023/8/16 8:55
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class In extends Column {

    /**
     * 包含的值集合
     */
    private Collection<Object> in;

}
