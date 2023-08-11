package com.refout.trace.datasource.convert.json;

import com.refout.trace.common.util.JsonUtil;
import jakarta.persistence.AttributeConverter;

public abstract class AbstractJsonConvert<J> implements AttributeConverter<J, String> {

    /**
     * Converts the value stored in the entity attribute into the
     * data representation to be stored in the database.
     *
     * @param attribute the entity attribute value to be converted
     * @return the converted data to be stored in the database
     * column
     */
    @Override
    public String convertToDatabaseColumn(J attribute) {
        return JsonUtil.toJson(attribute);
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
    public J convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return JsonUtil.fromJson(dbData, clazz());
    }

    protected abstract Class<J> clazz();

}
