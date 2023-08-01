package com.refout.trace.authentication.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 密码验证器
 *
 * @author oo w
 * @version 1.0
 * @since 2023/8/1 8:58
 */
public class PasswordValidator {

    /**
     * 加密器
     */
    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * 验证密码是否有效
     *
     * @param password 密码
     * @return 密码是否有效的布尔值
     */
    public static boolean isPasswordValid(String password) {
        // 密码长度至少为8个字符
        if (password.length() < 8) {
            return false;
        }
        // 密码必须包含至少一个大写字母、一个小写字母和一个数字
        String pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$";
        return password.matches(pattern);
    }

    /**
     * 检查密码是否与加密后的密码匹配
     *
     * @param rawPassword     原始密码
     * @param encodedPassword 加密后的密码
     * @return 密码是否匹配的布尔值
     */
    public static boolean matches(CharSequence rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    /**
     * 对密码进行加密
     *
     * @param rawPassword 原始密码
     * @return 加密后的密码
     */
    public static String encode(CharSequence rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

}