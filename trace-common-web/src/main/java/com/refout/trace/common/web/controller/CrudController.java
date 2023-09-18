package com.refout.trace.common.web.controller;

import com.refout.trace.datasource.domain.AbstractEntity;
import com.refout.trace.datasource.domain.query.QueryRequest;
import com.refout.trace.datasource.domain.query.page.PageIn;
import com.refout.trace.datasource.domain.query.page.PageOut;
import com.refout.trace.datasource.service.CrudService;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

/**
 * 抽象控制器类，用于处理通用的CRUD操作。
 * <p>
 * 子类需要实现dbBaseService()方法，返回对应的数据库查询service。
 * <p>
 * 子类可以通过继承该类来快速创建控制器，并实现通用的CRUD接口。
 *
 * @param <T>  实体类型
 * @param <ID> 实体的ID类型
 * @version 1.0
 * @since 2023/8/21 22:26
 */
public interface CrudController<T extends AbstractEntity, ID extends Serializable> {

    /**
     * 获取数据库查询service
     *
     * @return 数据库查询service
     */
    CrudService<T, ID> dbBaseService();

    /**
     * 分页查询实体对象
     *
     * @param pageIn 分页查询条件
     * @return 分页查询结果
     */
    @PostMapping("/page")
    default PageOut<T> page(@RequestBody PageIn<T, QueryRequest<T>> pageIn) {
        return dbBaseService().getPage(pageIn);
    }

    /**
     * 根据ID获取实体对象
     *
     * @param id 实体对象的ID
     * @return 对应的实体对象
     */
    @GetMapping("/{id}")
    default T get(@PathVariable ID id) {
        return dbBaseService().getById(id);
    }

    /**
     * 添加实体对象
     *
     * @param t 实体对象
     * @return 添加后的实体对象
     */
    @PostMapping
    default T add(@RequestBody T t) {
        return dbBaseService().save(t);
    }

    /**
     * 编辑实体对象
     *
     * @param t 实体对象
     * @return 编辑后的实体对象
     */
    @PutMapping
    default T edit(@RequestBody T t) {
        return dbBaseService().save(t);
    }

    /**
     * 根据ID删除实体对象
     *
     * @param id 实体对象的ID
     */
    @DeleteMapping("/{id}")
    default void delete(@PathVariable ID id) {
        dbBaseService().deleteById(id);
    }

    /**
     * 根据ID列表批量删除实体对象
     *
     * @param ids 实体对象的ID列表
     */
    @DeleteMapping("/all/{ids}")
    default void delete(@PathVariable List<ID> ids) {
        dbBaseService().deleteAllById(ids);
    }

}
