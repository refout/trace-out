package com.refout.trace.authentication.controller;

import com.refout.trace.authentication.domain.CaptchaResponse;
import com.refout.trace.authentication.domain.LoginRequest;
import com.refout.trace.authentication.domain.LoginResponse;
import com.refout.trace.authentication.domain.RegisterRequest;
import com.refout.trace.authentication.domain.RegisterResponse;
import com.refout.trace.authentication.service.AuthenticationService;
import com.refout.trace.common.web.domain.Result;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 控制器类
 * <p>
 * 该类用于处理身份验证相关的请求
 *
 * @author oo w
 * @version 1.0
 * @since 2023/5/12 16:15
 */
@RestController
public class Controller {

	@Resource
	private AuthenticationService authenticationService;

	/**
	 * 获取验证码
	 *
	 * @return {@link Result}响应结果
	 */
	@GetMapping("/captcha")
	public CaptchaResponse captcha() {
		return authenticationService.captcha();
	}

	/**
	 * 登录
	 *
	 * @param loginRequest {@link LoginRequest}登录请求
	 * @return {@link Result}响应结果
	 */
	@PostMapping("/login")
	public LoginResponse login(@RequestBody LoginRequest loginRequest) {
		return authenticationService.login(loginRequest);
	}

	/**
	 * 登出
	 */
	@DeleteMapping("/logout")
	public void logout() {
		authenticationService.logout();
	}

	/**
	 * 注册
	 *
	 * @param registerRequest {@link RegisterRequest}注册请求
	 * @return {@link Result}响应结果
	 */
	@PostMapping("/register")
	public RegisterResponse register(@RequestBody RegisterRequest registerRequest) {
		return authenticationService.register(registerRequest);
	}

}