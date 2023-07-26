package com.refout.trace.common.system.domain;

import com.refout.trace.datasource.domain.AbstractEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@Table(schema = "ts_config")
@Entity
public class Config extends AbstractEntity {

	private String key;

	private String value;

}
