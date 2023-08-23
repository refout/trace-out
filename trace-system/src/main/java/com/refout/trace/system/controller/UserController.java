package com.refout.trace.system.controller;

import com.refout.trace.common.system.domain.User;
import com.refout.trace.common.system.service.UserService;
import com.refout.trace.datasource.service.DbBaseService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController<User, Long> {

    @Resource
    private UserService userService;

    /**
     * 数据库查询service
     *
     * @return {@link DbBaseService}
     */
    @Override
    protected DbBaseService<User, Long> dbBaseService() {
        return userService;
    }

}
