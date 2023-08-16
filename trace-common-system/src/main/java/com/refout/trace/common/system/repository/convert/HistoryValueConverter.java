package com.refout.trace.common.system.repository.convert;

import com.refout.trace.common.system.domain.Config;
import com.refout.trace.datasource.convert.json.AbstractJsonConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class HistoryValueConverter extends AbstractJsonConverter<Config.HistoryValue> {

    @Override
    protected Class<Config.HistoryValue> clazz() {
        return Config.HistoryValue.class;
    }

}
