package com.refout.trace.common.util;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

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

    @Test
    public void testHasTextWhenTextIsNullThenThrowsException() {
        Function<String, RuntimeException> exceptionInstance = RuntimeException::new;
        String text = null;
        Supplier<String> messageSupplier = () -> "Text is null";

        assertThrows(RuntimeException.class, () -> Assert.hasText(exceptionInstance, text, messageSupplier), "Text is null");
    }

    @Test
    public void testHasTextWhenTextIsEmptyThenThrowsException() {
        Function<String, RuntimeException> exceptionInstance = RuntimeException::new;
        String text = "";
        Supplier<String> messageSupplier = () -> "Text is empty";

        assertThrows(RuntimeException.class, () -> Assert.hasText(exceptionInstance, text, messageSupplier), "Text is empty");
    }

    @Test
    public void testHasTextWhenTextIsNotEmptyThenDoesNotThrowException() {
        Function<String, RuntimeException> exceptionInstance = RuntimeException::new;
        String text = "Hello World";
        Supplier<String> messageSupplier = () -> "Text is not empty";

        assertDoesNotThrow(() -> Assert.hasText(exceptionInstance, text, messageSupplier));
    }

    @Test
    public void testNoNullElementsWhenCollectionIsNullThenExceptionThrown() {
        Function<String, RuntimeException> exceptionInstance = RuntimeException::new;
        Supplier<String> messageSupplier = () -> "Collection is null";
        Collection<String> collection = null;

        assertThrows(RuntimeException.class, () -> Assert.noNullElements(exceptionInstance, collection, messageSupplier), "Collection is null");
    }

    @Test
    public void testNoNullElementsWhenCollectionHasNullElementThenExceptionThrown() {
        Function<String, RuntimeException> exceptionInstance = RuntimeException::new;
        Supplier<String> messageSupplier = () -> "Collection has null element";
        Collection<String> collectionWithNull = Arrays.asList("Hello", null, "World");

        assertThrows(RuntimeException.class, () -> Assert.noNullElements(exceptionInstance, collectionWithNull, messageSupplier), "Collection has null element");
    }

    @Test
    public void testNoNullElementsWhenCollectionHasNoNullElementThenNoExceptionThrown() {
        Function<String, RuntimeException> exceptionInstance = RuntimeException::new;
        Supplier<String> messageSupplier = () -> "Collection has no null element";
        Collection<String> collectionWithoutNull = Arrays.asList("Hello", "World");

        assertDoesNotThrow(() -> Assert.noNullElements(exceptionInstance, collectionWithoutNull, messageSupplier));
    }

    @Test
    public void testNoNullElementsWhenArrayContainsNullThenThrowException() {
        Function<String, RuntimeException> exceptionInstance = RuntimeException::new;
        Object[] array = new Object[]{1, null, 3};
        Supplier<String> messageSupplier = () -> "Array contains null";

        assertThrows(RuntimeException.class, () -> Assert.noNullElements(exceptionInstance, array, messageSupplier), "Array contains null");
    }

    @Test
    public void testNoNullElementsWhenArrayDoesNotContainNullThenNoException() {
        Function<String, RuntimeException> exceptionInstance = RuntimeException::new;
        Object[] array = new Object[]{1, 2, 3};
        Supplier<String> messageSupplier = () -> "Array does not contain null";

        assertDoesNotThrow(() -> Assert.noNullElements(exceptionInstance, array, messageSupplier));
    }

    @Test
    public void testNoNullElementsWhenArrayIsNullThenThrowException() {
        Function<String, RuntimeException> exceptionInstance = RuntimeException::new;
        Object[] array = null;
        Supplier<String> messageSupplier = () -> "Array is null";

        assertThrows(RuntimeException.class, () -> Assert.noNullElements(exceptionInstance, array, messageSupplier), "Array is null");
    }

    @Test
    public void testNotEmptyWhenMapIsNotEmptyThenNoException() {
        Function<String, RuntimeException> exceptionInstance = RuntimeException::new;
        Map<String, String> map = new HashMap<>();
        map.put("key", "value");
        Supplier<String> messageSupplier = () -> "Map is not empty";

        assertDoesNotThrow(() -> Assert.notEmpty(exceptionInstance, map, messageSupplier));
    }

    @Test
    public void testNotEmptyWhenMapIsEmptyThenException() {
        Function<String, RuntimeException> exceptionInstance = RuntimeException::new;
        Map<String, String> map = Collections.emptyMap();
        Supplier<String> messageSupplier = () -> "Map is empty";

        assertThrows(RuntimeException.class, () -> Assert.notEmpty(exceptionInstance, map, messageSupplier), "Map is empty");
    }

    @Test
    public void testNotEmptyWhenMapIsNullThenException() {
        Function<String, RuntimeException> exceptionInstance = RuntimeException::new;
        Map<String, String> map = null;
        Supplier<String> messageSupplier = () -> "Map is null";

        assertThrows(RuntimeException.class, () -> Assert.notEmpty(exceptionInstance, map, messageSupplier), "Map is null");
    }

    @Test
    public void testNotEmptyWhenMessageSupplierIsNullThenNoException() {
        Function<String, RuntimeException> exceptionInstance = RuntimeException::new;
        Map<String, String> map = new HashMap<>();
        map.put("key", "value");
        Supplier<String> messageSupplier = null;

        assertDoesNotThrow(() -> Assert.notEmpty(exceptionInstance, map, messageSupplier));
    }

    @Test
    public void testNotEmptyWhenCollectionIsNullThenThrowsException() {
        Function<String, RuntimeException> exceptionInstance = RuntimeException::new;
        ArrayList<String> collection = null;
        Supplier<String> messageSupplier = () -> "Collection is null";

        assertThrows(RuntimeException.class, () -> Assert.notEmpty(exceptionInstance, collection, messageSupplier), "Collection is null");
    }

    @Test
    public void testNotEmptyWhenCollectionIsEmptyThenThrowsException() {
        Function<String, RuntimeException> exceptionInstance = RuntimeException::new;
        ArrayList<String> collection = new ArrayList<>();
        Supplier<String> messageSupplier = () -> "Collection is empty";

        assertThrows(RuntimeException.class, () -> Assert.notEmpty(exceptionInstance, collection, messageSupplier), "Collection is empty");
    }

    @Test
    public void testNotEmptyWhenCollectionIsNotEmptyThenNoException() {
        Function<String, RuntimeException> exceptionInstance = RuntimeException::new;
        ArrayList<String> collection = new ArrayList<>();
        collection.add("Hello");
        Supplier<String> messageSupplier = () -> "Collection is not empty";

        assertDoesNotThrow(() -> Assert.notEmpty(exceptionInstance, collection, messageSupplier));
    }

    @Test
    public void testNotEmptyWhenMessageSupplierIsNotNullThenNoException() {
        Function<String, RuntimeException> exceptionInstance = RuntimeException::new;
        ArrayList<String> collection = new ArrayList<>();
        collection.add("Hello");
        Supplier<String> messageSupplier = () -> "Message supplier is not null";

        assertDoesNotThrow(() -> Assert.notEmpty(exceptionInstance, collection, messageSupplier));
    }

    @Test
    public void testDoesNotContainWhenTextContainsSubstringThenThrowsException() {
        Function<String, RuntimeException> exceptionInstance = RuntimeException::new;
        String textToSearch = "Hello World";
        String substring = "World";
        Supplier<String> messageSupplier = () -> "Text contains substring";

        assertThrows(RuntimeException.class, () -> Assert.doesNotContain(exceptionInstance, textToSearch, substring, messageSupplier), "Text contains substring");
    }

    @Test
    public void testDoesNotContainWhenTextDoesNotContainSubstringThenNoException() {
        Function<String, RuntimeException> exceptionInstance = RuntimeException::new;
        String textToSearch = "Hello World";
        String substring = "Universe";
        Supplier<String> messageSupplier = () -> "Text does not contain substring";

        assertDoesNotThrow(() -> Assert.doesNotContain(exceptionInstance, textToSearch, substring, messageSupplier));
    }

    @Test
    public void testDoesNotContainWhenTextToSearchIsNullThenNoException() {
        Function<String, RuntimeException> exceptionInstance = RuntimeException::new;
        String textToSearch = null;
        String substring = "World";
        Supplier<String> messageSupplier = () -> "Text is null";

        assertDoesNotThrow(() -> Assert.doesNotContain(exceptionInstance, textToSearch, substring, messageSupplier));
    }

    @Test
    public void testNotEmptyWhenArrayIsNotNullAndNotEmptyThenNoException() {
        Function<String, RuntimeException> exceptionInstance = RuntimeException::new;
        Object[] array = new Object[]{"Hello", "World"};
        Supplier<String> messageSupplier = () -> "Array is not null and not empty";

        assertDoesNotThrow(() -> Assert.notEmpty(exceptionInstance, array, messageSupplier));
    }

    @Test
    public void testNotEmptyWhenArrayIsNullThenException() {
        Function<String, RuntimeException> exceptionInstance = RuntimeException::new;
        Object[] array = null;
        Supplier<String> messageSupplier = () -> "Array is null";

        assertThrows(RuntimeException.class, () -> Assert.notEmpty(exceptionInstance, array, messageSupplier), "Array is null");
    }

    @Test
    public void testNotEmptyWhenArrayIsNotNullButEmptyThenException() {
        Function<String, RuntimeException> exceptionInstance = RuntimeException::new;
        Object[] array = new Object[]{};
        Supplier<String> messageSupplier = () -> "Array is not null but empty";

        assertThrows(RuntimeException.class, () -> Assert.notEmpty(exceptionInstance, array, messageSupplier), "Array is not null but empty");
    }

    @Test
    public void testHasLengthWhenTextIsNullThenThrowsException() {
        Function<String, RuntimeException> exceptionInstance = RuntimeException::new;
        String text = null;
        Supplier<String> messageSupplier = () -> "Text is null";

        assertThrows(RuntimeException.class, () -> Assert.hasLength(exceptionInstance, text, messageSupplier), "Text is null");
    }

    @Test
    public void testHasLengthWhenTextIsEmptyThenThrowsException() {
        Function<String, RuntimeException> exceptionInstance = RuntimeException::new;
        String text = "";
        Supplier<String> messageSupplier = () -> "Text is empty";

        assertThrows(RuntimeException.class, () -> Assert.hasLength(exceptionInstance, text, messageSupplier), "Text is empty");
    }

    @Test
    public void testHasLengthWhenTextIsNotEmptyThenNoException() {
        Function<String, RuntimeException> exceptionInstance = RuntimeException::new;
        String text = "Hello World";
        Supplier<String> messageSupplier = () -> "Text is not empty";

        assertDoesNotThrow(() -> Assert.hasLength(exceptionInstance, text, messageSupplier));
    }

    @Test
    public void testNotNullWhenObjectIsNullThenThrowsException() {
        Function<String, RuntimeException> exceptionInstance = RuntimeException::new;
        Object object = null;
        String message = "Object is null";

        assertThrows(RuntimeException.class, () -> Assert.notNull(exceptionInstance, object, message), "Object is null");
    }

    @Test
    public void testNotNullWhenObjectIsNotNullThenDoesNotThrowException() {
        Function<String, RuntimeException> exceptionInstance = RuntimeException::new;
        Object object = new Object();
        String message = "Object is not null";

        assertDoesNotThrow(() -> Assert.notNull(exceptionInstance, object, message));
    }

    @Test
    public void testNotNullWhenObjectIsNullThenThrowsExceptionWithCorrectMessage() {
        Function<String, RuntimeException> exceptionInstance = RuntimeException::new;
        Object object = null;
        Supplier<String> messageSupplier = () -> "Object is null";

        Exception exception = assertThrows(RuntimeException.class, () -> Assert.notNull(exceptionInstance, object, messageSupplier));
        assert exception.getMessage().equals("Object is null");
    }

    @Test
    public void testNotNullWhenMessageSupplierIsNullThenDoesNotThrowException() {
        Function<String, RuntimeException> exceptionInstance = RuntimeException::new;
        Object object = new Object();
        Supplier<String> messageSupplier = null;

        assertDoesNotThrow(() -> Assert.notNull(exceptionInstance, object, messageSupplier));
    }

    @Test
    public void testIsNullWhenObjectIsNullThenNoException() {
        Function<String, RuntimeException> exceptionInstance = RuntimeException::new;
        Object object = null;
        Supplier<String> messageSupplier = () -> "Object is null";

        assertDoesNotThrow(() -> Assert.isNull(exceptionInstance, object, messageSupplier));
    }

    @Test
    public void testIsNullWhenObjectIsNotNullThenException() {
        Function<String, RuntimeException> exceptionInstance = RuntimeException::new;
        Object object = new Object();
        Supplier<String> messageSupplier = () -> "Object is not null";

        assertThrows(RuntimeException.class, () -> Assert.isNull(exceptionInstance, object, messageSupplier), "Object is not null");
    }

    @Test
    public void testIsNullWhenMessageSupplierIsNullThenExceptionWithNullMessage() {
        Function<String, RuntimeException> exceptionInstance = RuntimeException::new;
        Object object = new Object();
        Supplier<String> messageSupplier = null;

        assertThrows(RuntimeException.class, () -> Assert.isNull(exceptionInstance, object, messageSupplier));
    }

    @Test
    public void testIsTrueWhenExpressionIsFalseThenThrowsException() {
        Function<String, RuntimeException> exceptionInstance = RuntimeException::new;
        Boolean expression = false;
        Supplier<String> messageSupplier = () -> "Expression is false";

        assertThrows(RuntimeException.class, () -> Assert.isTrue(exceptionInstance, expression, messageSupplier), "Expression is false");
    }

    @Test
    public void testIsTrueWhenExpressionIsTrueThenNoException() {
        Function<String, RuntimeException> exceptionInstance = RuntimeException::new;
        Boolean expression = true;
        Supplier<String> messageSupplier = () -> "Expression is true";

        assertDoesNotThrow(() -> Assert.isTrue(exceptionInstance, expression, messageSupplier));
    }

    @Test
    public void testIsTrueWhenExpressionIsNullThenThrowsException() {
        Function<String, RuntimeException> exceptionInstance = RuntimeException::new;
        Boolean expression = null;
        Supplier<String> messageSupplier = () -> "Expression is null";

        assertThrows(RuntimeException.class, () -> Assert.isTrue(exceptionInstance, expression, messageSupplier), "Expression is null");
    }

}