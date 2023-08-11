package com.refout.trace.system.controller;

import com.refout.trace.common.system.domain.User;
import com.refout.trace.common.system.service.UserService;
import com.refout.trace.common.web.domain.Result;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/system/user")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/page")
    public Result page(User user) {
        PageRequest pageRequest = PageRequest.of(0, 2);

        Page<User> page = userService.getPage(user, pageRequest);
        return Result.success(page);
    }
}
