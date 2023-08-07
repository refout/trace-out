package com.refout.trace.common.system.domain;


import com.refout.trace.datasource.domain.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@Table(name = "ts_api")
@Entity
public class Api extends AbstractEntity {

	@Column(name = "api_name")
	private String apiName;
	@Column(name = "path")
	private String path;

	private String state;

	private String permission;

	private String remark;

}
