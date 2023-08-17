package com.refout.trace.common.web.constant;

import com.refout.trace.common.util.StrUtil;
import com.refout.trace.common.web.util.jwt.JwtUtil;
import com.refout.trace.redis.constant.CacheKeyRule;
import io.jsonwebtoken.Claims;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AuthCacheKey extends CacheKeyRule {

    /**
     * 缓存键的前缀
     */
    protected static final String PREFIX = "AUTH";

    protected static final String USER = "USER";

    public static @NotNull String userKey(final String tokenId) {
        return key(PREFIX, USER, tokenId);
    }

    @Nullable
    public static String userKeyFromToken(@Nullable final String token) {
        Claims claims = JwtUtil.parseToken(token);
        if (claims == null) {
            return null;
        }

        String id = claims.getId();
        if (!StrUtil.hasText(id)) {
            return null;
        }

        return AuthCacheKey.userKey(id);
    }

}
