package com.refout.trace.common.web.constant;


import com.refout.trace.redis.constant.CacheKeyRule;

public class AuthCacheKey extends CacheKeyRule {

    /**
     * 缓存键的前缀
     */
    protected static final String PREFIX = "AUTH";

    protected static final String USER = "USER";

    public static String userKey(final String tokenId) {
        return key(PREFIX, USER, tokenId);
    }

}
