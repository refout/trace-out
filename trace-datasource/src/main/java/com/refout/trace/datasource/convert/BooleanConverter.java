package com.refout.trace.datasource.convert;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * 用于将Boolean类型属性转换为Integer类型存储在数据库中的转换器。
 * 实现了AttributeConverter接口。
 * 使用@Converter(autoApply = true)注解，表示该转换器将自动应用于所有适用的实体属性。
 *
 * @author oo w
 * @version 1.0
 * @since 2023/8/16 21:08
 */
@Converter(autoApply = true)
public class BooleanConverter implements AttributeConverter<Boolean, Integer> {

    /**
     * Converts the value stored in the entity attribute into the
     * data representation to be stored in the database.
     *
     * @param attribute the entity attribute value to be converted
     * @return the converted data to be stored in the database
     * column
     */
    @Override
    public Integer convertToDatabaseColumn(Boolean attribute) {
        return attribute == null || !attribute ? 0 : 1;
    }

    /**
     * Converts the data stored in the database column into the
     * value to be stored in the entity attribute.
     * Note that it is the responsibility of the converter writer to
     * specify the correct <code>dbData</code> type for the corresponding
     * column for use by the JDBC driver: i.e., persistence providers are
     * not expected to do such type conversion.
     *
     * @param dbData the data from the database column to be
     *               converted
     * @return the converted value to be stored in the entity
     * attribute
     */
    @Override
    public Boolean convertToEntityAttribute(Integer dbData) {
        return dbData != null && dbData != 1;
    }

}
