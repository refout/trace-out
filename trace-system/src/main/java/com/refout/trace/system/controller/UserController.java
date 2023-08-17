package com.refout.trace.system.controller;

import com.refout.trace.common.system.domain.User;
import com.refout.trace.common.system.service.UserService;
import com.refout.trace.datasource.domain.query.QueryRequest;
import com.refout.trace.datasource.domain.query.page.PageIn;
import com.refout.trace.datasource.domain.query.page.PageOut;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/page")
    public PageOut<User> page(@RequestBody PageIn<User, QueryRequest<User>> pageIn) {
        return userService.getPage(pageIn);
    }

}
