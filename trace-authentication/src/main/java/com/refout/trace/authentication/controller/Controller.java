package com.refout.trace.authentication.controller;

import com.refout.trace.authentication.domain.*;
import com.refout.trace.authentication.service.AuthenticationService;
import com.refout.trace.common.web.domain.Result;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * TODO
 *
 * @author oo w
 * @version 1.0
 * @since 2023/5/12 16:15
 */
@RestController
public class Controller {

	@Resource
	private AuthenticationService authenticationService;

	@GetMapping("/captcha")
	public Result<CaptchaResponse> captcha() {
		return Result.success(authenticationService.captcha());
	}

	@PostMapping("/login")
	public Result<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
		return Result.success(authenticationService.login(loginRequest));
	}

	@PostMapping("/register")
	public Result<RegisterResponse> register(@RequestBody RegisterRequest registerRequest) {
		return Result.success(authenticationService.register(registerRequest));
	}

}
