package com.refout.trace.datasource.convert.json;

import com.refout.trace.common.util.JsonUtil;
import jakarta.persistence.AttributeConverter;

/**
 * 抽象JSON转换器类，用于将实体属性的值转换为数据库中存储的数据表示形式。
 *
 * @param <J> JSON类型
 * @author oo w
 * @version 1.0
 * @since 2023/8/12 23:42
 */
public abstract class AbstractJsonConverter<J> implements AttributeConverter<J, String> {

    /**
     * 将实体属性的值转换为数据库中存储的数据表示形式。
     *
     * @param attribute 实体属性的值
     * @return 存储在数据库列中的转换后的数据
     */
    @Override
    public String convertToDatabaseColumn(J attribute) {
        return JsonUtil.toJson(attribute);
    }

    /**
     * 将数据库列中存储的数据转换为实体属性的值。
     * 注意，转换器的编写者有责任为JDBC驱动程序指定正确的<code>dbData</code>类型，以用于对应列的使用。
     * 持久化提供程序不会执行此类类型转换。
     *
     * @param dbData 数据库列中的数据
     * @return 存储在实体属性中的转换后的值
     */
    @Override
    public J convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return JsonUtil.fromJson(dbData, clazz());
    }

    /**
     * 获取JSON类型的Class对象。
     *
     * @return JSON类型的Class对象
     */
    protected abstract Class<J> clazz();

}
