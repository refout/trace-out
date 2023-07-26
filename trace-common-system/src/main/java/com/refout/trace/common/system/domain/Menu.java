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
@Accessors(chain = true, fluent = true)
@Table(schema = "trace", name = "ts_menu")
@Entity(name = "ts_menu")
public class Menu extends AbstractEntity {

	@Column(name = "menu_name")
	private String menuName;

	private Long parentId;

	private String path;

	private String component;

	private String query;

	private Boolean frame;

	private Boolean cache;

	private String menuType;

	private String visible;

	private String state;

	private String permission;

	private String icon;

	private String remark;

}
