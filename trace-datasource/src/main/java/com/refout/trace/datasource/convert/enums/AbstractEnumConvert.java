package com.refout.trace.datasource.convert.enums;

import com.refout.trace.common.enums.AbstractDbEnum;
import com.refout.trace.datasource.exception.DatasourceException;
import jakarta.persistence.AttributeConverter;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractEnumConvert<E extends AbstractDbEnum>
        implements AttributeConverter<E, String> {

    @Override
    public String convertToDatabaseColumn(@NotNull E attribute) {
        return attribute.getCode();
    }

    @Override
    public E convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }

        @NotNull E[] enums = getEnums();
        for (E e : enums) {
            if (dbData.equalsIgnoreCase(e.getCode())) {
                return e;
            }
        }

        throw new DatasourceException("无效的枚举值:" + dbData);
    }

    @NotNull
    protected abstract E[] getEnums();

}
