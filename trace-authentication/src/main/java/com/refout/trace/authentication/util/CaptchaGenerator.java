package com.refout.trace.authentication.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Random;


/**
 * 验证码生成器
 *
 * @author oo w
 * @version 1.0
 * @since 2023/6/26 12:04
 */
public class CaptchaGenerator {

	/**
	 * 验证码图片宽度
	 */
	private static final int WIDTH = 120;

	/**
	 * 验证码图片高度
	 */
	private static final int HEIGHT = 40;

	/**
	 * 验证码长度
	 */
	private static final int LENGTH = 4;

	/**
	 * 干扰线数量
	 */
	private static final int LINE_COUNT = 20;

	/**
	 * 验证码字符集
	 */
	private static final String CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

	/**
	 * 生成验证码
	 *
	 * @return 验证码和验证码图片的base64字符串
	 */
	@Contract(" -> new")
	public static @NotNull Captcha generate() {
		BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		String captcha = generateCaptcha(image);
		String imageBase64 = imageToBase64(image);

		image.flush();
		return new Captcha(captcha, imageBase64);
	}

	/**
	 * 生成验证码图片和验证码文本
	 *
	 * @param image 验证码图片
	 * @return 验证码文本
	 */
	private static @NotNull String generateCaptcha(@NotNull BufferedImage image) {
		Graphics graphics = image.getGraphics();
		Random random = new Random();
		// 填充背景色
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, WIDTH, HEIGHT);
		// 画干扰线
		for (int i = 0; i < LINE_COUNT; i++) {
			int x1 = random.nextInt(WIDTH);
			int y1 = random.nextInt(HEIGHT);
			int x2 = random.nextInt(WIDTH);
			int y2 = random.nextInt(HEIGHT);
			graphics.setColor(getRandomColor());
			graphics.drawLine(x1, y1, x2, y2);
		}
		// 生成验证码
		StringBuilder captcha = new StringBuilder();
		for (int i = 0; i < LENGTH; i++) {
			char c = CHARACTERS.charAt(random.nextInt(CHARACTERS.length()));
			captcha.append(c);
			graphics.setColor(getRandomColor());
			graphics.setFont(new Font("Arial", Font.BOLD, 30));
			graphics.drawString(String.valueOf(c), 20 * i + 10, 30);
		}
		graphics.dispose();
		return captcha.toString();
	}

	/**
	 * 生成随机颜色
	 *
	 * @return 随机颜色
	 */
	private static @NotNull Color getRandomColor() {
		Random random = new Random();
		return new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
	}

	/**
	 * 将图片转换成base64字符串
	 *
	 * @param image 图片
	 * @return base64字符串
	 */
	private static @NotNull String imageToBase64(BufferedImage image) {
		// 图片格式
		String format = "png";
		String base64prefix = "data:image/" + format + ";base64,";
		byte[] imageBytes = null;

		// 将图片转换成字节数组
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ImageIO.write(image, format, bos);
			imageBytes = bos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 将字节数组转换成base64字符串
		return base64prefix + Base64.getEncoder().encodeToString(imageBytes);
	}

	/**
	 * 验证码
	 *
	 * @param captchaText 验证码文本
	 * @param imageBase64 验证码图片base64
	 */
	public record Captcha(String captchaText, String imageBase64) {

	}

}