package com.refout.trace.datasource.domain;

import com.refout.trace.datasource.handler.snowflake.SnowflakeId;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class AbstractEntity implements Persistable<Long>, Serializable {

  @Id
  @GenericGenerator(type = SnowflakeId.class, name = "snowflakeId")
  @GeneratedValue(generator = "snowflakeId")
  private Long id;

  /**
   * 创建时间
   */
  @CreatedDate
  private LocalDateTime createTime;

  /**
   * 创建人
   */
  @CreatedBy
  private String createBy;

  /**
   * 更新时间
   */
  @LastModifiedDate
  private LocalDateTime updateTime;

  /**
   * 更新人
   */
  @LastModifiedBy
  private String updateBy;

  /**
   * 逻辑删除（0：未删除；1：已删除）
   */
  private Boolean deleted;

  /**
   * Returns the id of the entity.
   *
   * @return the id. Can be {@literal null}.
   */
  @Override
  public Long getId() {
    return id;
  }

  @Transient
  private boolean isNew = true;

  @Override
  public boolean isNew() {
    return isNew;
  }

  @PrePersist
  @PostLoad
  void markNotNew() {
    this.isNew = false;
  }

}

