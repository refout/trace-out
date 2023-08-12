package com.refout.trace.datasource.repository;

import com.refout.trace.datasource.domain.AbstractEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * TODO
 *
 * @author oo w
 * @version 1.0
 * @since 2023/8/11 22:45
 */
public interface BaseRepository<T extends AbstractEntity, ID> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

}
