package com.refout.trace.authentication.util;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PasswordValidatorTest {

    @Test
    public void testIsPasswordValidWithValidPasswordShouldReturnTrue() {
        // Arrange
        String password = "Abcdefg1";
        // Act
        boolean isValid = PasswordValidator.isPasswordValid(password);
        // Assert
        Assertions.assertTrue(isValid);
    }

    @Test
    public void testIsPasswordValidWithInvalidPasswordLengthShouldReturnFalse() {
        // Arrange
        String password = "Abcdefg";
        // Act
        boolean isValid = PasswordValidator.isPasswordValid(password);
        // Assert
        Assertions.assertFalse(isValid);
    }

    @Test
    public void testIsPasswordValidWithInvalidPasswordFormatShouldReturnFalse() {
        // Arrange
        String password = "abcdefg1";
        // Act
        boolean isValid = PasswordValidator.isPasswordValid(password);
        // Assert
        Assertions.assertFalse(isValid);
    }

    @Test
    public void testMatchesWithMatchingPasswordsShouldReturnTrue() {
        // Arrange
        String rawPassword = "Abcdefg1";
        String encodedPassword = PasswordValidator.encode(rawPassword);
        // Act
        boolean matches = PasswordValidator.matches(rawPassword, encodedPassword);
        // Assert
        Assertions.assertTrue(matches);
    }

    @Test
    public void testMatchesWithNonMatchingPasswordsShouldReturnFalse() {
        // Arrange
        String rawPassword = "Abcdefg1";
        String encodedPassword = PasswordValidator.encode(rawPassword);
        // Act
        boolean matches = PasswordValidator.matches("WrongPassword", encodedPassword);
        // Assert
        Assertions.assertFalse(matches);
    }

    @Test
    public void testEncodeWithValidPasswordShouldReturnEncodedPassword() {
        // Arrange
        String rawPassword = "Abcdefg1";
        // Act
        String encodedPassword = PasswordValidator.encode(rawPassword);
        // Assert
        Assertions.assertNotEquals(rawPassword, encodedPassword);
    }

}