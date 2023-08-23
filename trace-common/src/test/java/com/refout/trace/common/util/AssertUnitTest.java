package com.refout.trace.common.util;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AssertUnitTest {

    @Test
    void isTrueWithTrueExpressionShouldNotThrowException() {
        // Arrange
        Boolean expression = true;
        String message = "Expression is not true";
        Function<String, RuntimeException> exceptionInstance = RuntimeException::new;

        // Act & Assert
        assertDoesNotThrow(() -> Assert.isTrue(exceptionInstance, expression, message));
    }

    @Test
    void isTrueWithFalseExpressionShouldThrowException() {
        // Arrange
        Boolean expression = false;
        String message = "Expression is not true";
        Function<String, RuntimeException> exceptionInstance = RuntimeException::new;

        // Act & Assert
        assertThrows(RuntimeException.class, () -> Assert.isTrue(exceptionInstance, expression, message));
    }

    @Test
    void isNullWithNullObjectShouldNotThrowException() {
        // Arrange
        Object object = null;
        String message = "Object is not null";
        Function<String, RuntimeException> exceptionInstance = RuntimeException::new;

        // Act & Assert
        assertDoesNotThrow(() -> Assert.isNull(exceptionInstance, object, message));
    }

    @Test
    void isNullWithNonNullObjectShouldThrowException() {
        // Arrange
        Object object = new Object();
        String message = "Object is not null";
        Function<String, RuntimeException> exceptionInstance = RuntimeException::new;

        // Act & Assert
        assertThrows(RuntimeException.class, () -> Assert.isNull(exceptionInstance, object, message));
    }

    @Test
    void notNullWithNonNullObjectShouldNotThrowException() {
        // Arrange
        Object object = new Object();
        String message = "Object is null";
        Function<String, RuntimeException> exceptionInstance = RuntimeException::new;

        // Act & Assert
        assertDoesNotThrow(() -> Assert.notNull(exceptionInstance, object, message));
    }

    @Test
    void notNullWithNullObjectShouldThrowException() {
        // Arrange
        Object object = null;
        String message = "Object is null";
        Function<String, RuntimeException> exceptionInstance = RuntimeException::new;

        // Act & Assert
        assertThrows(RuntimeException.class, () -> Assert.notNull(exceptionInstance, object, message));
    }

    @Test
    void hasLengthWithNonNullStringWithLengthShouldNotThrowException() {
        // Arrange
        String text = "Hello";
        String message = "String has no length";
        Function<String, RuntimeException> exceptionInstance = RuntimeException::new;

        // Act & Assert
        assertDoesNotThrow(() -> Assert.hasLength(exceptionInstance, text, message));
    }

    @Test
    void hasLengthWithNullStringShouldThrowException() {
        // Arrange
        String text = null;
        String message = "String has no length";
        Function<String, RuntimeException> exceptionInstance = RuntimeException::new;

        // Act & Assert
        assertThrows(RuntimeException.class, () -> Assert.hasLength(exceptionInstance, text, message));
    }

    @Test
    void hasLengthWithEmptyStringShouldThrowException() {
        // Arrange
        String text = "";
        String message = "String has no length";
        Function<String, RuntimeException> exceptionInstance = RuntimeException::new;

        // Act & Assert
        assertThrows(RuntimeException.class, () -> Assert.hasLength(exceptionInstance, text, message));
    }

    @Test
    void hasTextWithNonNullStringWithTextShouldNotThrowException() {
        // Arrange
        String text = "Hello";
        String message = "String has no text";
        Function<String, RuntimeException> exceptionInstance = RuntimeException::new;

        // Act & Assert
        assertDoesNotThrow(() -> Assert.hasText(exceptionInstance, text, message));
    }

    @Test
    void hasTextWithNullStringShouldThrowException() {
        // Arrange
        String text = null;
        String message = "String has no text";
        Function<String, RuntimeException> exceptionInstance = RuntimeException::new;

        // Act & Assert
        assertThrows(RuntimeException.class, () -> Assert.hasText(exceptionInstance, text, message));
    }

    @Test
    void hasTextWithEmptyStringShouldThrowException() {
        // Arrange
        String text = "";
        String message = "String has no text";
        Function<String, RuntimeException> exceptionInstance = RuntimeException::new;

        // Act & Assert
        assertThrows(RuntimeException.class, () -> Assert.hasText(exceptionInstance, text, message));
    }

    @Test
    void doesNotContainWithStringNotContainingSubstringShouldNotThrowException() {
        // Arrange
        String textToSearch = "Hello World";
        String substring = "Java";
        String message = "String contains substring";
        Function<String, RuntimeException> exceptionInstance = RuntimeException::new;

        // Act & Assert
        assertDoesNotThrow(() -> Assert.doesNotContain(exceptionInstance, textToSearch, substring, message));
    }

    @Test
    void doesNotContainWithStringContainingSubstringShouldThrowException() {
        // Arrange
        String textToSearch = "Hello World";
        String substring = "World";
        String message = "String contains substring";
        Function<String, RuntimeException> exceptionInstance = RuntimeException::new;

        // Act & Assert
        assertThrows(RuntimeException.class, () -> Assert.doesNotContain(exceptionInstance, textToSearch, substring, message));
    }

    @Test
    void notEmptyWithNonEmptyArrayShouldNotThrowException() {
        // Arrange
        Object[] array = {1, 2, 3};
        String message = "Array is empty";
        Function<String, RuntimeException> exceptionInstance = RuntimeException::new;

        // Act & Assert
        assertDoesNotThrow(() -> Assert.notEmpty(exceptionInstance, array, message));
    }

    @Test
    void notEmptyWithEmptyArrayShouldThrowException() {
        // Arrange
        Object[] array = {};
        String message = "Array is empty";
        Function<String, RuntimeException> exceptionInstance = RuntimeException::new;

        // Act & Assert
        assertThrows(RuntimeException.class, () -> Assert.notEmpty(exceptionInstance, array, message));
    }

    @Test
    void noNullElementsWithArrayContainingNoNullElementsShouldNotThrowException() {
        // Arrange
        Object[] array = {1, 2, 3};
        String message = "Array contains null elements";
        Function<String, RuntimeException> exceptionInstance = RuntimeException::new;

        // Act & Assert
        assertDoesNotThrow(() -> Assert.noNullElements(exceptionInstance, array, message));
    }

    @Test
    void noNullElementsWithArrayContainingNullElementShouldThrowException() {
        // Arrange
        Object[] array = {1, null, 3};
        String message = "Array contains null elements";
        Function<String, RuntimeException> exceptionInstance = RuntimeException::new;

        // Act & Assert
        assertThrows(RuntimeException.class, () -> Assert.noNullElements(exceptionInstance, array, message));
    }

    @Test
    void notEmptyWithNonEmptyCollectionShouldNotThrowException() {
        // Arrange
        Collection<Integer> collection = Arrays.asList(1, 2, 3);
        String message = "Collection is empty";
        Function<String, RuntimeException> exceptionInstance = RuntimeException::new;

        // Act & Assert
        assertDoesNotThrow(() -> Assert.notEmpty(exceptionInstance, collection, message));
    }

    @Test
    void notEmptyWithEmptyCollectionShouldThrowException() {
        // Arrange
        Collection<Integer> collection = Collections.emptyList();
        String message = "Collection is empty";
        Function<String, RuntimeException> exceptionInstance = RuntimeException::new;

        // Act & Assert
        assertThrows(RuntimeException.class, () -> Assert.notEmpty(exceptionInstance, collection, message));
    }

    @Test
    void noNullElementsWithCollectionContainingNoNullElementsShouldNotThrowException() {
        // Arrange
        Collection<Integer> collection = Arrays.asList(1, 2, 3);
        String message = "Collection contains null elements";
        Function<String, RuntimeException> exceptionInstance = RuntimeException::new;

        // Act & Assert
        assertDoesNotThrow(() -> Assert.noNullElements(exceptionInstance, collection, message));
    }

    @Test
    void noNullElementsWithCollectionContainingNullElementShouldThrowException() {
        // Arrange
        Collection<Integer> collection = Arrays.asList(1, null, 3);
        String message = "Collection contains null elements";
        Function<String, RuntimeException> exceptionInstance = RuntimeException::new;

        // Act & Assert
        assertThrows(RuntimeException.class, () -> Assert.noNullElements(exceptionInstance, collection, message));
    }

    @Test
    void notEmptyWithNonEmptyMapShouldNotThrowException() {
        // Arrange
        Map<String, Integer> map = new HashMap<>();
        map.put("one", 1);
        map.put("two", 2);
        map.put("three", 3);
        String message = "Map is empty";
        Function<String, RuntimeException> exceptionInstance = RuntimeException::new;

        // Act & Assert
        assertDoesNotThrow(() -> Assert.notEmpty(exceptionInstance, map, message));
    }

    @Test
    void notEmptyWithEmptyMapShouldThrowException() {
        // Arrange
        Map<String, Integer> map = Collections.emptyMap();
        String message = "Map is empty";
        Function<String, RuntimeException> exceptionInstance = RuntimeException::new;

        // Act & Assert
        assertThrows(RuntimeException.class, () -> Assert.notEmpty(exceptionInstance, map, message));
    }

}