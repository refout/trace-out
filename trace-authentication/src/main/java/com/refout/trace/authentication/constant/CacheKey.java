package com.refout.trace.authentication.constant;


import com.refout.trace.redis.constant.CacheKeyRule;

public class CacheKey extends CacheKeyRule {

    /**
     * 缓存键的前缀
     */
    private static final String PREFIX = "AUTH";

    private static final String USER = "USER";

    public static String userKey(final String tokenId) {
        return key(PREFIX, USER, tokenId);
    }

    public static String passwordErrorCountKey(final String username) {
        return key(PREFIX, USER, "PASSWORD_ERROR_COUNT", username);
    }

    public static String captchaKey(String captchaId) {
        return key(PREFIX, USER, "CAPTCHA", captchaId);
    }

}
