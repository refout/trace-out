package com.refout.trace.datasource.convert.enums;

import com.refout.trace.common.enums.StateEnum;
import jakarta.persistence.Converter;

/**
 * 状态枚举转换器类，用于将状态枚举类型转换为数据库列的值。
 *
 * @author oo w
 * @version 1.0
 * @since 2023/8/12 23:42
 */
@Converter(autoApply = true)
public class StateEnumConvert extends AbstractEnumConvert<StateEnum> {

    /**
     * 获取状态枚举类型的数组。
     *
     * @return 状态枚举类型的数组
     */
    @Override
    protected StateEnum[] getEnums() {
        return StateEnum.values();
    }

}
