package com.refout.trace.authentication.domain;

/**
 * 登录请求体
 *
 * @param principal   用户身份信息
 * @param credentials 用户凭证信息
 * @param captchaId   验证码ID
 * @param captchaCode 用户输入的验证码
 * @author oo w
 * @version 1.0
 * @since 2023/5/15 20:36
 */
public record LoginRequest(String principal, String credentials,
                           String captchaId, String captchaCode) {
}
