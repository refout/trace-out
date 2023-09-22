package com.refout.trace.address.domain;

import com.refout.trace.datasource.domain.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 地址信息
 *
 * @author oo w
 * @version 1.0
 * @since 2023/9/19 10:16
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@Table(name = "tb_address")
@Entity
public class Address extends AbstractEntity {

    @Column(name = "parent_id", nullable = false)
    private Long parentId;

    @Column(name = "name", nullable = false, length = 128)
    private String name;

}