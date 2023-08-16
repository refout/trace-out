package com.refout.trace.datasource.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.refout.trace.datasource.annotation.Deleted;
import com.refout.trace.datasource.convert.BooleanConverter;
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
import org.springframework.format.annotation.DateTimeFormat;

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
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss.SSS")
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
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss.SSS")
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
    @Deleted
    @Convert(converter = BooleanConverter.class)
    @Column(name = "deleted", nullable = false)
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