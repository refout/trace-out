package com.refout.trace.datasource.service;

import com.refout.trace.datasource.domain.AbstractEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DbBaseService<T extends AbstractEntity, ID> {

    JpaRepository<T, ID> repository();

    default T save(T t) {
        return repository().save(t);
    }

    default List<T> saveAll(List<T> ts) {
        return repository().saveAll(ts);
    }

}
