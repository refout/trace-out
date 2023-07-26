package com.refout.trace.common.util;

import java.util.Random;
import java.util.UUID;

/**
 * 随机工具类
 * <p>
 * 该工具类提供了生成随机数的方法。
 * 使用java.util.Random类生成随机数。
 *
 * @author oo w
 * @version 1.0
 * @since 2023/7/16 17:39
 */
public class RandomUtil {

    /**
     * 随机数生成器
     */
    private static final Random random = new Random();

    /**
     * 生成指定范围内的随机整数。
     *
     * @param min 最小值 （包含）
     * @param max 最大值 （包含）
     * @return 生成的随机整数
     */
    public static int randomInt(int min, int max) {
        return random.nextInt(min, max + 1);
    }

    /**
     * 生成指定范围内的随机整数。
     *
     * @param min 最小值 （包含）
     * @param max 最大值 （包含）
     * @return 生成的随机整数
     */
    public static long randomLong(long min, long max) {
        return random.nextLong(min, max + 1);
    }

    public static String randomUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }
}
