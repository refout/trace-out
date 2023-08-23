package com.refout.trace.system.controller;

import com.refout.trace.datasource.domain.AbstractEntity;
import com.refout.trace.datasource.domain.query.QueryRequest;
import com.refout.trace.datasource.domain.query.page.PageIn;
import com.refout.trace.datasource.domain.query.page.PageOut;
import com.refout.trace.datasource.service.DbBaseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * TODO
 *
 * @author oo w
 * @version 1.0
 * @since 2023/8/21 22:26
 */
public abstract class BaseController<T extends AbstractEntity, ID> {

    /**
     * 数据库查询service
     *
     * @return {@link DbBaseService}
     */
    protected abstract DbBaseService<T, ID> dbBaseService();

    @PostMapping("/page")
    public PageOut<T> page(@RequestBody PageIn<T, QueryRequest<T>> pageIn) {
        return dbBaseService().getPage(pageIn);
    }

    @GetMapping("/{id}")
    public T get(@PathVariable ID id) {
        return dbBaseService().getById(id);
    }

    @PostMapping
    public T add(T t) {
        return dbBaseService().save(t);
    }

    @PutMapping
    public T edit(T t) {
        return dbBaseService().save(t);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable ID id) {
        dbBaseService().deleteById(id);
    }

    @DeleteMapping("/all/{ids}")
    public void delete(@PathVariable List<ID> ids) {
        dbBaseService().deleteAllById(ids);
    }

}
