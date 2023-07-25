package com.refout.trace.authentication.domain;

/**
 * 验证码
 *
 * @param captchaId   验证码id
 * @param imageBase64 验证码图片base64
 * @author oo w
 * @version 1.0
 * @since 2023/6/26 12:06
 */
public record CaptchaResponse(String captchaId, String imageBase64) {

}