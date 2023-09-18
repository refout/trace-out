package com.refout.trace.system.controller;

import com.refout.trace.common.system.domain.Api;
import com.refout.trace.common.web.controller.CrudController;
import com.refout.trace.datasource.service.CrudService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 接口控制器类，用于处理接口相关的请求。
 * <p>
 * 继承自{@link CrudController}类，实现了通用的CRUD操作接口。
 *
 * @author oo w
 * @version 1.0
 * @since 2023/8/27 15:34
 */
@RestController
@RequestMapping("/api")
public class ApiController implements CrudController<Api, Long> {

    /**
     * 获取数据库查询service
     *
     * @return 数据库查询service
     */
    @Override
    public CrudService<Api, Long> dbBaseService() {
        return null;
    }

}
