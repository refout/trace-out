package com.refout.trace.datasource.service;

import com.refout.trace.datasource.domain.AbstractEntity;
import com.refout.trace.datasource.domain.page.PageIn;
import com.refout.trace.datasource.repository.BaseRepository;
import jakarta.persistence.Column;
import jakarta.persistence.criteria.Predicate;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ReflectionUtils;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"UnusedReturnValue", "unused"})
public interface DbBaseService<T extends AbstractEntity, ID> {

    BaseRepository<T, ID> repository();

    default T getById(ID id) {
        return repository().getReferenceById(id);
    }

    default void deleteById(ID id) {
        repository().deleteById(id);
    }

    default void deleteAllById(List<ID> ids) {
        repository().deleteAllById(ids);
    }

    default T save(T t) {
        return repository().save(t);
    }

    default List<T> saveAll(List<T> ts) {
        return repository().saveAll(ts);
    }

    default Page<T> getPage(@NotNull PageIn<T> pageIn) {
        Specification<T> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            Object condition = pageIn.query();
            Class<?> clazz = condition.getClass();
            ReflectionUtils.doWithFields(clazz, field -> {
                Column column = field.getDeclaredAnnotation(Column.class);
                if (column == null) {
                    return;
                }
                field.setAccessible(true);
                Object value = field.get(condition);
                if (value == null) {
                    return;
                }
                predicates.add(criteriaBuilder.like(root.get(column.name()), "%" + value + "%"));
            });

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        return repository().findAll(specification, pageIn.pageable());
    }

}
