package com.refout.trace.system.controller;

import com.refout.trace.common.system.domain.User;
import com.refout.trace.common.system.service.UserService;
import com.refout.trace.common.web.controller.AbstractController;
import com.refout.trace.datasource.service.DbService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
