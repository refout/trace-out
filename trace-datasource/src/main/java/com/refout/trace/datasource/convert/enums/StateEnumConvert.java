package com.refout.trace.datasource.convert.enums;

import com.refout.trace.common.enums.StateEnum;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class StateEnumConvert extends AbstractEnumConvert<StateEnum> {

    @Override
    protected StateEnum[] getEnums() {
        return StateEnum.values();
    }

}
