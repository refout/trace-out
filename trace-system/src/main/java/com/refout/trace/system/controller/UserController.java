package com.refout.trace.system.controller;

import com.refout.trace.common.system.domain.User;
import com.refout.trace.common.system.service.UserService;
import com.refout.trace.common.web.controller.AbstractController;
import com.refout.trace.datasource.service.DbService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户控制器类，用于处理用户相关的请求。
 * <p>
 * 继承自{@link AbstractController}类，实现了通用的CRUD操作接口。
 *
 * @author oo w
 * @version 1.0
 * @since 2023/8/27 15:34
 */
@RestController
@RequestMapping("/user")
public class UserController extends AbstractController<User, Long> {

    @Resource
    private UserService userService;

    /**
     * 数据库查询service
     *
     * @return {@link DbService}
     */
    @Override
    protected DbService<User, Long> dbBaseService() {
        return userService;
    }

}
