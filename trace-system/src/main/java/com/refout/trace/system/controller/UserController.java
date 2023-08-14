package com.refout.trace.system.controller;

import com.refout.trace.common.system.domain.User;
import com.refout.trace.common.system.service.UserService;
import com.refout.trace.common.web.domain.Result;
import com.refout.trace.datasource.domain.page.PageIn;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/system/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/page")
    public Result page(@RequestBody PageIn<User> pageIn) {
        Page<User> page = userService.getPage(pageIn);
        return Result.success(page);
    }

}
