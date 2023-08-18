package com.refout.trace.common.util;

import com.refout.trace.common.exception.AbstractExceptionAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AssertUnitTest {

    @Test
    public void testOfWithMessageFunc() {
        AbstractExceptionAssert assertInstance = Assert.of(CustomException::new);
        Assertions.assertNotNull(assertInstance);
    }

    @Test
    public void testOfWithExceptionClass() {
        AbstractExceptionAssert assertInstance = Assert.of(CustomException.class);
        Assertions.assertNotNull(assertInstance);
    }

    @Test
    public void testExceptionInstanceWithMessageFunc() {
        String errorMessage = "Test error message";
        AbstractExceptionAssert assertInstance = Assert.of(CustomException::new);
        Assertions.assertThrows(CustomException.class, () -> assertInstance.isTrue(false, errorMessage));
    }

    @Test
    public void testExceptionInstanceWithExceptionClass() {
        String errorMessage = "Test error message";
        AbstractExceptionAssert assertInstance = Assert.of(CustomException.class);
        Assertions.assertThrows(CustomException.class, () -> assertInstance.isTrue(false, errorMessage));
    }

    private static class CustomException extends RuntimeException {

        public CustomException(String message) {
            super(message);
        }

    }

}