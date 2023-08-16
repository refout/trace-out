package com.refout.trace.datasource.repository;

import com.refout.trace.datasource.domain.AbstractEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * BaseRepository接口是一个基础的仓库接口，继承自JpaRepository和JpaSpecificationExecutor接口。
 * 它用于操作数据库中的实体对象，并提供基本的CRUD操作。
 *
 * @param <T>  实体类型
 * @param <ID> 实体的ID类型
 * @author oo w
 * @version 1.0
 * @see JpaRepository
 * @see JpaSpecificationExecutor
 * @since 2023/8/11 22:45
 */
@NoRepositoryBean
public interface BaseRepository<T extends AbstractEntity, ID> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

}
