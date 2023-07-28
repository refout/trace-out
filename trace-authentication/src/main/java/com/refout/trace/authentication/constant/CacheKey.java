package com.refout.trace.authentication.constant;


import com.refout.trace.common.web.constant.AuthCacheKey;
import com.refout.trace.redis.constant.CacheKeyRule;

public class CacheKey extends AuthCacheKey {

    public static String passwordErrorCountKey(final String username) {
        return key(PREFIX, USER, "PASSWORD_ERROR_COUNT", username);
    }

    public static String captchaKey(String captchaId) {
        return key(PREFIX, USER, "CAPTCHA", captchaId);
    }

}
