package com.refout.trace.datasource.convert.enums;

import com.refout.trace.common.enums.AbstractDbEnum;
import com.refout.trace.datasource.exception.DatasourceException;
import jakarta.persistence.AttributeConverter;
import org.jetbrains.annotations.NotNull;

/**
 * 抽象枚举转换器类，用于将枚举类型转换为数据库列的值。
 *
 * @param <E> 枚举类型
 * @author oo w
 * @version 1.0
 * @since 2023/8/12 23:42
 */
public abstract class AbstractEnumConverter<E extends AbstractDbEnum> implements AttributeConverter<E, String> {

    /**
     * 将枚举类型转换为数据库列的值。
     *
     * @param attribute 枚举类型的属性
     * @return 数据库列的值
     */
    @Override
    public String convertToDatabaseColumn(@NotNull E attribute) {
        return attribute.getCode();
    }

    /**
     * 将数据库列的值转换为枚举类型。
     *
     * @param dbData 数据库列的值
     * @return 枚举类型
     * @throws DatasourceException 如果数据库列的值无效，则抛出异常
     */
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

    /**
     * 获取枚举类型的数组。
     *
     * @return 枚举类型的数组
     */
    @NotNull
    protected abstract E[] getEnums();

}