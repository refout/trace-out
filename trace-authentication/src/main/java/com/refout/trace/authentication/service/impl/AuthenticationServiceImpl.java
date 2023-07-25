package com.refout.trace.authentication.service.impl;

import com.refout.trace.authentication.domain.*;
import com.refout.trace.authentication.service.AuthenticationService;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    /**
     * 验证码生成
     *
     * @return 验证码响应数据
     */
    @Override
    public CaptchaResponse captcha() {
        return null;
    }

    /**
     * 登录
     *
     * @param loginRequest 登录请求信息
     * @return 登录响应
     */
    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        return null;
    }

    /**
     * 注册
     *
     * @param registerRequest 注册请求信息
     * @return 注册响应
     */
    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {
        return null;
    }

}
