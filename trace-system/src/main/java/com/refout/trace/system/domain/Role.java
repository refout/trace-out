package com.refout.trace.system.domain;


import com.refout.trace.common.enums.StateEnum;
import com.refout.trace.datasource.domain.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 角色信息
 *
 * @author oo w
 * @version 1.0
 * @since 2023/8/27 15:43
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@Table(name = "ts_role")
@Entity
public class Role extends AbstractEntity {

    /**
     * 角色名称
     */
    @Column(name = "role_name", nullable = false)
    private String roleName;

    /**
     * 状态（0：正常；1：停用）
     */
    @Column(name = "state", nullable = false, columnDefinition = "char(1) default '0'")
    private StateEnum state;

    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;

}