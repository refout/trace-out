package com.refout.trace.authentication.util;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PasswordValidatorTest {

    @Test
    public void testIsPasswordValid_WithValidPassword_ShouldReturnTrue() {
        // Arrange
        String password = "Abcdefg1";
        // Act
        boolean isValid = PasswordValidator.isPasswordValid(password);
        // Assert
        Assertions.assertTrue(isValid);
    }

    @Test
    public void testIsPasswordValid_WithInvalidPasswordLength_ShouldReturnFalse() {
        // Arrange
        String password = "Abcdefg";
        // Act
        boolean isValid = PasswordValidator.isPasswordValid(password);
        // Assert
        Assertions.assertFalse(isValid);
    }

    @Test
    public void testIsPasswordValid_WithInvalidPasswordFormat_ShouldReturnFalse() {
        // Arrange
        String password = "abcdefg1";
        // Act
        boolean isValid = PasswordValidator.isPasswordValid(password);
        // Assert
        Assertions.assertFalse(isValid);
    }

    @Test
    public void testMatches_WithMatchingPasswords_ShouldReturnTrue() {
        // Arrange
        String rawPassword = "Abcdefg1";
        String encodedPassword = PasswordValidator.encode(rawPassword);
        // Act
        boolean matches = PasswordValidator.matches(rawPassword, encodedPassword);
        // Assert
        Assertions.assertTrue(matches);
    }

    @Test
    public void testMatches_WithNonMatchingPasswords_ShouldReturnFalse() {
        // Arrange
        String rawPassword = "Abcdefg1";
        String encodedPassword = PasswordValidator.encode(rawPassword);
        // Act
        boolean matches = PasswordValidator.matches("WrongPassword", encodedPassword);
        // Assert
        Assertions.assertFalse(matches);
    }

    @Test
    public void testEncode_WithValidPassword_ShouldReturnEncodedPassword() {
        // Arrange
        String rawPassword = "Abcdefg1";
        // Act
        String encodedPassword = PasswordValidator.encode(rawPassword);
        // Assert
        Assertions.assertNotEquals(rawPassword, encodedPassword);
    }

}