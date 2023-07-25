package com.refout.trace.authentication.service;

import com.refout.trace.authentication.domain.*;

public interface AuthenticationService {

    /**
     * 验证码生成
     *
     * @return 验证码响应数据
     */
    CaptchaResponse captcha();

    /**
     * 登录
     *
     * @param loginRequest 登录请求信息
     * @return 登录响应
     */
    LoginResponse login(final LoginRequest loginRequest);

    /**
     * 注册
     *
     * @param registerRequest 注册请求信息
     * @return 注册响应
     */
    RegisterResponse register(final RegisterRequest registerRequest);

}
