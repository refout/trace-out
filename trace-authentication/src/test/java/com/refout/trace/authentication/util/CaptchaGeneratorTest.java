package com.refout.trace.authentication.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CaptchaGeneratorTest {

    @Test
    public void testGenerate() {
        CaptchaGenerator.Captcha captcha = CaptchaGenerator.generate();
        // 验证生成的验证码长度是否正确
        Assertions.assertEquals(4, captcha.captchaText().length());
        // 验证生成的验证码图片base64字符串是否以指定的前缀开头
        Assertions.assertTrue(captcha.imageBase64().startsWith("data:image/png;base64,"));
    }

}