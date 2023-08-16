package com.refout.trace.datasource.domain.query;

import com.refout.trace.datasource.domain.query.condition.In;
import com.refout.trace.datasource.domain.query.condition.Scope;
import lombok.Data;

import java.util.List;

/**
 * 查询请求
 *
 * @param <D> 数据类型
 */
@Data
public class QueryRequest<D> {

    /**
     * 数据
     */
    private D data;

    /**
     * 范围条件列表
     */
    private List<Scope> scopes;

    /**
     * 包含条件列表
     */
    private List<In> ins;

}
