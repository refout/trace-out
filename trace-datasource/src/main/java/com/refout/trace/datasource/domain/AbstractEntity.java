package com.refout.trace.datasource.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.refout.trace.datasource.handler.snowflake.SnowflakeId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 抽象实体类
 *
 * @author oo w
 * @version 1.0
 * @since 2023/7/31 12:03
 */
@Accessors(chain = true)
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractEntity implements Persistable<Long>, Serializable {

    /**
     * ID
     */
    @Id
    @GenericGenerator(type = SnowflakeId.class, name = "snowflakeId")
    @GeneratedValue(generator = "snowflakeId")
    @Column(name = "id")
    private Long id;

    /**
     * 创建时间
     */
    @CreatedDate
    @Column(name = "create_time")
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    @CreatedBy
    @Column(name = "create_by")
    private String createBy;

    /**
     * 更新时间
     */
    @LastModifiedDate
    @Column(name = "update_time")
    private LocalDateTime updateTime;

    /**
     * 更新人
     */
    @LastModifiedBy
    @Column(name = "update_by")
    private String updateBy;

    /**
     * 逻辑删除（0：未删除；1：已删除）
     */
    @Column(name = "deleted", nullable = false, columnDefinition = "tinyint(1) default 0")
    private boolean deleted;

    /**
     * 获取ID
     *
     * @return ID
     */
    @Override
    public Long getId() {
        return id;
    }

    /**
     * 是否为新实体
     */
    @JsonIgnore
    @Transient
    private boolean isNew = true;

    /**
     * 判断实体是否为新实体
     *
     * @return 是否为新实体
     */
    @JsonIgnore
    @Override
    public boolean isNew() {
        return isNew;
    }

    /**
     * 标记实体为非新实体
     */
    @JsonIgnore
    @PrePersist
    @PostLoad
    void markNotNew() {
        this.isNew = false;
    }

}