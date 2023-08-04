package com.refout.trace.common.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RandomUtilTest {

    @Test
    void testRandomInt() {
        int result = RandomUtil.randomInt(1, 10);
        assertTrue(result >= 1 && result <= 10, "Generated random integer is not within the specified range");
    }

    @Test
    void testRandomLong() {
        long result = RandomUtil.randomLong(1000L, 2000L);
        assertTrue(result >= 1000L && result <= 2000L, "Generated random long is not within the specified range");
    }

    @Test
    void testRandomUUID() {
        String uuid = RandomUtil.randomUUID();
        assertNotNull(uuid, "Generated UUID is null");
        assertEquals(32, uuid.length(), "Generated UUID length is not 32");
    }

}