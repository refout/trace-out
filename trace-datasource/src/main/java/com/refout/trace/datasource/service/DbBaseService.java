package com.refout.trace.datasource.service;

import com.refout.trace.common.util.DateUtil;
import com.refout.trace.datasource.annotation.Deleted;
import com.refout.trace.datasource.annotation.Query;
import com.refout.trace.datasource.domain.AbstractEntity;
import com.refout.trace.datasource.domain.query.QueryRequest;
import com.refout.trace.datasource.domain.query.condition.In;
import com.refout.trace.datasource.domain.query.condition.Scope;
import com.refout.trace.datasource.domain.query.page.PageIn;
import com.refout.trace.datasource.domain.query.page.PageOut;
import com.refout.trace.datasource.enums.QueryFunc;
import com.refout.trace.datasource.repository.BaseRepository;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.Transient;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ReflectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.refout.trace.datasource.enums.QueryFunc.LIKE;

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
    default <Q extends QueryRequest<T>> PageOut<T> getPage(@NotNull PageIn<T, Q> pageIn) {
        Q condition = pageIn.query();
        if (condition == null) {
            return null;
        }
        T data = condition.getData();

        Class<?> clazz = data.getClass();
        // 创建Specification对象，用于构建查询条件
        Specification<T> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            ReflectionUtils.doWithFields(clazz, field -> {
                field.setAccessible(true);
                Object value = field.get(data);
                if (value == null) {
                    return;
                }

                Transient transientAnnotation = field.getDeclaredAnnotation(Transient.class);
                if (transientAnnotation != null) {
                    return;
                }
                Deleted deleted = field.getDeclaredAnnotation(Deleted.class);
                if (deleted != null) {
                    field.setBoolean(data, false);
                }
                Convert convert = field.getDeclaredAnnotation(Convert.class);
                if (convert != null) {
                    try {
                        @SuppressWarnings("unchecked")
                        Class<? extends AttributeConverter<Object, Object>> converter = convert.converter();
                        AttributeConverter<Object, Object> attributeConverter =
                                converter.getDeclaredConstructor().newInstance();
                        value = attributeConverter.convertToDatabaseColumn(value);
                        System.out.println();
                    } catch (Exception ignore) {

                    }
                }
                // 获取字段上的Query注解，用于指定查询方式
                Query queryAnnotation = field.getDeclaredAnnotation(Query.class);

                // 根据Query注解和字段值构建查询条件
                Predicate predicate = predicate(criteriaBuilder, root.get(field.getName()), queryAnnotation, value);
                predicates.add(predicate);
            });

            predicate(predicates, criteriaBuilder, root, condition);

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        // 调用Repository的findAll方法进行分页查询
        Page<T> page = repository().findAll(specification, pageIn.pageable());
        return PageOut.of(
                page.getContent(), page.getNumber(), page.getSize(),
                page.getTotalElements(), page.getTotalPages()
        );

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
    private Predicate predicate(
            CriteriaBuilder criteriaBuilder, Expression<String> expression, Query query, Object value) {
        QueryFunc func = query == null ? LIKE : query.value();
        return switch (func) {
            case LIKE -> criteriaBuilder.like(expression, "%" + value + "%");
            case LEFT_LIKE -> criteriaBuilder.like(expression, "%" + value);
            case RIGHT_LIKE -> criteriaBuilder.like(expression, value + "%");
            case NOT_LIKE -> criteriaBuilder.notLike(expression, "%" + value + "%");
            case EQ -> criteriaBuilder.equal(expression, value);
            case NOT_EQ -> criteriaBuilder.notEqual(expression, value);
        };
    }

    /**
     * 根据查询条件生成谓词
     *
     * @param predicates      谓词列表
     * @param criteriaBuilder 谓词构建器
     * @param root            根对象
     * @param condition       查询条件
     * @param <D>             数据类型
     * @param <Q>             查询请求类型
     */
    private <D, Q extends QueryRequest<D>> void predicate(
            List<Predicate> predicates, CriteriaBuilder criteriaBuilder, Root<T> root, @NotNull Q condition) {
        List<In> ins = condition.getIns();
        if (ins != null && !ins.isEmpty()) {
            List<Predicate> list = ins.stream()
                    .map(in -> root.get(in.getColumnName()).in(in.getIn()))
                    .toList();
            predicates.addAll(list);
        }
        List<Scope> scopes = condition.getScopes();
        if (scopes != null && !scopes.isEmpty()) {
            List<Predicate> list = scopes.stream()
                    .map(scope -> {
                        Object start = scope.getStart();
                        Object end = scope.getEnd();

                        Class<?> javaType = root.get(scope.getColumnName()).getJavaType();
                        if (javaType == LocalDate.class && start instanceof String strrtStr && end instanceof String endStr) {
                            start = DateUtil.toLocalDate(strrtStr);
                            end = DateUtil.toLocalDate(endStr);
                        }

                        if (javaType == LocalDateTime.class && start instanceof String startStr && end instanceof String endStr) {
                            start = DateUtil.toLocalDateTime(startStr);
                            end = DateUtil.toLocalDateTime(endStr);
                        }

                        if (start instanceof @SuppressWarnings("rawtypes")Comparable lower &&
                                end instanceof @SuppressWarnings("rawtypes")Comparable upper) {
                            @SuppressWarnings("unchecked")
                            Predicate between = criteriaBuilder.between(root.get(scope.getColumnName()), lower, upper);
                            return between;
                        }
                        if (start instanceof @SuppressWarnings("rawtypes")Comparable lower) {
                            @SuppressWarnings("unchecked")
                            Predicate predicate = criteriaBuilder.greaterThanOrEqualTo(root.get(scope.getColumnName()), lower);
                            return predicate;
                        }
                        if (end instanceof @SuppressWarnings("rawtypes")Comparable upper) {
                            @SuppressWarnings("unchecked")
                            Predicate predicate = criteriaBuilder.lessThanOrEqualTo(root.get(scope.getColumnName()), upper);
                            return predicate;
                        }
                        return null;
                    })
                    .filter(Objects::nonNull)
                    .toList();
            predicates.addAll(list);
        }
    }

}