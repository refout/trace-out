package com.refout.trace.authentication.constant;


import com.refout.trace.common.web.constant.AuthCacheKey;

public class CacheKey extends AuthCacheKey {

    public static String passwordErrorCountKey(final String username) {
        return key(PREFIX, USER, "PASSWORD_ERROR_COUNT", username);
    }

    public static String captchaErrorCountKey(final String captchaId) {
        return key(PREFIX, USER, "CAPTCHA_ERROR_COUNT", captchaId);
    }

    public static String captchaKey(String captchaId) {
        return key(PREFIX, USER, "CAPTCHA", captchaId);
    }

}
