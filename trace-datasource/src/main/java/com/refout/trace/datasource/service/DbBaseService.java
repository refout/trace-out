package com.refout.trace.datasource.service;

import com.refout.trace.common.util.StrUtil;
import com.refout.trace.datasource.annotation.Query;
import com.refout.trace.datasource.domain.AbstractEntity;
import com.refout.trace.datasource.domain.page.PageIn;
import com.refout.trace.datasource.enums.QueryFunc;
import com.refout.trace.datasource.repository.BaseRepository;
import jakarta.persistence.Column;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ReflectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据库查询service
 *
 * @author oo w
 * @version 1.0
 * @since 2023/8/12 23:06
 */
@SuppressWarnings({"UnusedReturnValue", "unused"})
public interface DbBaseService<T extends AbstractEntity, ID> {

    /**
     * 获取对应实体类的Repository
     *
     * @return 实体类对应的Repository
     */
    BaseRepository<T, ID> repository();

    /**
     * 根据ID获取实体对象
     *
     * @param id 实体对象的ID
     * @return 对应的实体对象
     */
    default T getById(ID id) {
        return repository().getReferenceById(id);
    }

    /**
     * 根据ID删除实体对象
     *
     * @param id 实体对象的ID
     */
    default void deleteById(ID id) {
        repository().deleteById(id);
    }

    /**
     * 根据ID列表批量删除实体对象
     *
     * @param ids 实体对象的ID列表
     */
    default void deleteAllById(List<ID> ids) {
        repository().deleteAllById(ids);
    }

    /**
     * 保存实体对象
     *
     * @param t 实体对象
     * @return 保存后的实体对象
     */
    default T save(T t) {
        return repository().save(t);
    }

    /**
     * 批量保存实体对象
     *
     * @param ts 实体对象列表
     * @return 保存后的实体对象列表
     */
    default List<T> saveAll(List<T> ts) {
        return repository().saveAll(ts);
    }

    /**
     * 分页查询实体对象
     *
     * @param pageIn 分页查询条件
     * @return 分页查询结果
     */
    default Page<T> getPage(@NotNull PageIn<T> pageIn) {
        // 创建Specification对象，用于构建查询条件
        Specification<T> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            Object condition = pageIn.query();
            Class<?> clazz = condition.getClass();
            ReflectionUtils.doWithFields(clazz, field -> {
                field.setAccessible(true);
                Object value = field.get(condition);
                if (value == null) {
                    return;
                }

                // 获取字段上的Query注解，用于指定查询方式
                Query queryAnnotation = field.getDeclaredAnnotation(Query.class);

                // 获取字段上的Column注解，用于指定数据库列名
                Column column = field.getDeclaredAnnotation(Column.class);
                String fieldName = column == null || column.name().isBlank() ?
                        StrUtil.convertToSnakeCase(field.getName()) :
                        column.name();

                // 根据Query注解和字段值构建查询条件
                Predicate predicate = predicate(criteriaBuilder, root.get(fieldName), queryAnnotation, value);
                predicates.add(predicate);
            });

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        // 调用Repository的findAll方法进行分页查询
        return repository().findAll(specification, pageIn.pageable());
    }

    /**
     * 根据Query注解和字段值构建查询条件
     *
     * @param criteriaBuilder CriteriaBuilder对象
     * @param expression      查询表达式
     * @param query           Query注解
     * @param value           字段值
     * @return 构建的查询条件
     */
    private Predicate predicate(CriteriaBuilder criteriaBuilder, Expression<String> expression, Query query, Object value) {
        QueryFunc func = query == null ? QueryFunc.LIKE : query.value();
        return switch (func) {
            case LIKE -> criteriaBuilder.like(expression, "%" + value + "%");
            case LEFT_LIKE -> criteriaBuilder.like(expression, "%" + value);
            case RIGHT_LIKE -> criteriaBuilder.like(expression, value + "%");
            case NOT_LIKE -> criteriaBuilder.notLike(expression, "%" + value + "%");
            case EQ -> criteriaBuilder.equal(expression, value);
            case NOT_EQ -> criteriaBuilder.notEqual(expression, value);
        };
    }

}