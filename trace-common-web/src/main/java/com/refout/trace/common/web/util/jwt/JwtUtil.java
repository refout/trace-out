package com.refout.trace.common.web.util.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.apache.catalina.connector.RequestFacade;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * JWT 工具
 *
 * @author oo w
 * @version 1.0
 * @since 2023/4/30 18:27
 */
public class JwtUtil {

	public static final String AUTHORIZATION = "Authorization";

	public static final String BEARER = "Bearer ";

	/**
	 * 令牌秘钥随机字符串
	 */
	private static final String secretString = "w*wd*()svu237kmk']..;ASAH23423N,._+00-9@kh#$rejbvj%@kh$%!+_(jhf&(ty";

	/**
	 * 令牌秘钥
	 */
	private static final SecretKey key = Keys.hmacShaKeyFor(secretString.getBytes(StandardCharsets.UTF_8));

	/**
	 * 创建token
	 *
	 * @param jti              token id
	 * @return token
	 */
	public static String createToken(String jti) {
		return createToken(null, jti);
	}

	/**
	 * 创建token
	 *
	 * @param claims           数据载体
	 * @param jti              token id
	 * @return token
	 */
	public static String createToken(Map<String, Object> claims, String jti) {
		JwtBuilder jwtBuilder = Jwts.builder()
				.id(jti)
				.signWith(key);

		if (claims != null) {
			jwtBuilder.claims(claims);
		}
		return jwtBuilder.compact();
	}

	/**
	 * 解析token
	 *
	 * @param token token字符串
	 * @return 解析后的数据
	 */
	public static Claims parseToken(String token) {
		if (token == null || token.isBlank()) {
			return null;
		}
		try {
			JwtParser build = Jwts.parser().verifyWith(key).build();
			if (!build.isSigned(token)) {
				return null;
			}
			Jws<Claims> claimsJws = build.parseSignedClaims(token);
			return claimsJws.getPayload();
		} catch (JwtException e) {
			return null;
		}
	}

	public static String getToken(RequestFacade request) {
		// 从请求头中获取JWT令牌
		final String authorization = request.getHeader(JwtUtil.AUTHORIZATION);
		if (authorization == null || authorization.isBlank()) {
			return null;
		}

		// 如果JWT令牌不以"Bearer "开头
		if (!authorization.startsWith(JwtUtil.BEARER)) {
			return null;
		}

		return authorization.substring(JwtUtil.BEARER.length());
	}

}
