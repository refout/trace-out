package com.refout.trace.common.exception;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AbstractExceptionAssertUnitTest {

    private final TestExceptionAssert exceptionAssert = new TestExceptionAssert();

    @Test
    public void isTrueTestWithTrueExpressionShouldNotThrowException() {
        assertDoesNotThrow(() -> exceptionAssert.isTrue(true, "Test message"));
    }

    @Test
    public void isTrueTestWithFalseExpressionShouldThrowException() {
        assertThrows(CustomException.class, () -> exceptionAssert.isTrue(false, "Test message"));
    }

    @Test
    public void isTrueTestWithFalseExpressionAndSupplierShouldThrowExceptionWithSupplierMessage() {
        assertThrows(CustomException.class, () -> exceptionAssert.isTrue(false, () -> "Test message"));
    }

    @Test
    public void isNullTestWithNullObjectShouldNotThrowException() {
        assertDoesNotThrow(() -> exceptionAssert.isNull(null, "Test message"));
    }

    @Test
    public void isNullTestWithNonNullObjectShouldThrowException() {
        assertThrows(CustomException.class, () -> exceptionAssert.isNull(new Object(), "Test message"));
    }

    @Test
    public void isNullTestWithNonNullObjectAndSupplierShouldThrowExceptionWithSupplierMessage() {
        assertThrows(CustomException.class, () -> exceptionAssert.isNull(new Object(), () -> "Test message"));
    }

    @Test
    public void notNullTestWithNonNullObjectShouldNotThrowException() {
        assertDoesNotThrow(() -> exceptionAssert.notNull(new Object(), "Test message"));
    }

    @Test
    public void notNullTestWithNullObjectShouldThrowException() {
        assertThrows(CustomException.class, () -> exceptionAssert.notNull(null, "Test message"));
    }

    @Test
    public void notNullTestWithNullObjectAndSupplierShouldThrowExceptionWithSupplierMessage() {
        assertThrows(CustomException.class, () -> exceptionAssert.notNull(null, () -> "Test message"));
    }

    @Test
    public void hasLengthTestWithEmptyStringShouldThrowException() {
        assertThrows(CustomException.class, () -> exceptionAssert.hasLength("", "Test message"));
    }

    @Test
    public void hasLengthTestWithNonEmptyStringShouldNotThrowException() {
        assertDoesNotThrow(() -> exceptionAssert.hasLength("Hello", "Test message"));
    }

    @Test
    public void hasLengthTestWithEmptyStringAndSupplierShouldThrowExceptionWithSupplierMessage() {
        assertThrows(CustomException.class, () -> exceptionAssert.hasLength("", () -> "Test message"));
    }

    @Test
    public void hasTextTestWithBlankStringShouldThrowException() {
        assertThrows(CustomException.class, () -> exceptionAssert.hasText("   ", "Test message"));
    }

    @Test
    public void hasTextTestWithNonBlankStringShouldNotThrowException() {
        assertDoesNotThrow(() -> exceptionAssert.hasText("Hello", "Test message"));
    }

    @Test
    public void hasTextTestWithBlankStringAndSupplierShouldThrowExceptionWithSupplierMessage() {
        assertThrows(CustomException.class, () -> exceptionAssert.hasText("   ", () -> "Test message"));
    }

    @Test
    public void doesNotContainTestWithTextContainingSubstringShouldThrowException() {
        assertThrows(CustomException.class, () -> exceptionAssert.doesNotContain("Hello World", "World", "Test message"));
    }

    @Test
    public void doesNotContainTestWithTextNotContainingSubstringShouldNotThrowException() {
        assertDoesNotThrow(() -> exceptionAssert.doesNotContain("Hello World", "Java", "Test message"));
    }

    @Test
    public void doesNotContainTestWithTextContainingSubstringAndSupplierShouldThrowExceptionWithSupplierMessage() {
        assertThrows(CustomException.class, () -> exceptionAssert.doesNotContain("Hello World", "World", () -> "Test message"));
    }

    @Test
    public void notEmptyTestWithEmptyArrayShouldThrowException() {
        assertThrows(CustomException.class, () -> exceptionAssert.notEmpty(new Object[0], "Test message"));
    }

    @Test
    public void notEmptyTestWithNonEmptyArrayShouldNotThrowException() {
        assertDoesNotThrow(() -> exceptionAssert.notEmpty(new Object[]{1, 2, 3}, "Test message"));
    }

    @Test
    public void notEmptyTestWithEmptyArrayAndSupplierShouldThrowExceptionWithSupplierMessage() {
        assertThrows(CustomException.class, () -> exceptionAssert.notEmpty(new Object[0], () -> "Test message"));
    }

    @Test
    public void noNullElementsTestWithArrayContainingNullElementShouldThrowException() {
        assertThrows(CustomException.class, () -> exceptionAssert.noNullElements(new Object[]{1, null, 3}, "Test message"));
    }

    @Test
    public void noNullElementsTestWithArrayNotContainingNullElementShouldNotThrowException() {
        assertDoesNotThrow(() -> exceptionAssert.noNullElements(new Object[]{1, 2, 3}, "Test message"));
    }

    @Test
    public void noNullElementsTestWithArrayContainingNullElementAndSupplierShouldThrowExceptionWithSupplierMessage() {
        assertThrows(CustomException.class, () -> exceptionAssert.noNullElements(new Object[]{1, null, 3}, () -> "Test message"));
    }

    @Test
    public void notEmptyTestWithEmptyCollectionShouldThrowException() {
        assertThrows(CustomException.class, () -> exceptionAssert.notEmpty(Collections.emptyList(), "Test message"));
    }

    @Test
    public void notEmptyTestWithNonEmptyCollectionShouldNotThrowException() {
        assertDoesNotThrow(() -> exceptionAssert.notEmpty(Arrays.asList(1, 2, 3), "Test message"));
    }

    @Test
    public void notEmptyTestWithEmptyCollectionAndSupplierShouldThrowExceptionWithSupplierMessage() {
        assertThrows(CustomException.class, () -> exceptionAssert.notEmpty(Collections.emptyList(), () -> "Test message"));
    }

    @Test
    public void noNullElementsTestWithCollectionContainingNullElementShouldThrowException() {
        assertThrows(CustomException.class, () -> exceptionAssert.noNullElements(Arrays.asList(1, null, 3), "Test message"));
    }

    @Test
    public void noNullElementsTestWithCollectionNotContainingNullElementShouldNotThrowException() {
        assertDoesNotThrow(() -> exceptionAssert.noNullElements(Arrays.asList(1, 2, 3), "Test message"));
    }

    @Test
    public void noNullElementsTestWithCollectionContainingNullElementAndSupplierShouldThrowExceptionWithSupplierMessage() {
        assertThrows(CustomException.class, () -> exceptionAssert.noNullElements(Arrays.asList(1, null, 3), () -> "Test message"));
    }

    @Test
    public void notEmptyTestWithEmptyMapShouldThrowException() {
        assertThrows(CustomException.class, () -> exceptionAssert.notEmpty(Collections.emptyMap(), "Test message"));
    }

    @Test
    public void notEmptyTestWithNonEmptyMapShouldNotThrowException() {
        assertDoesNotThrow(() -> exceptionAssert.notEmpty(Collections.singletonMap("key", "value"), "Test message"));
    }

    @Test
    public void notEmptyTestWithEmptyMapAndSupplierShouldThrowExceptionWithSupplierMessage() {
        assertThrows(CustomException.class, () -> exceptionAssert.notEmpty(Collections.emptyMap(), () -> "Test message"));
    }

    private static class CustomException extends RuntimeException {

        public CustomException(String message) {
            super(message);
        }

    }

    private static class TestExceptionAssert extends AbstractExceptionAssert {

        @Override
        protected RuntimeException exceptionInstance(String message) {
            return new CustomException(message);
        }

    }

}

