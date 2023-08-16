package com.refout.trace.common.util;

import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.test.util.ReflectionTestUtils;

public class ConvertUtilTest {

    @Mock
    private ConversionService conversionService;

    @Mock
    private ConfigurableListableBeanFactory beanFactory;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        Mockito.when(beanFactory.getBean(ConversionService.class)).thenReturn(conversionService);
    }

    @Test
    void testConvert() {
//        Mockito.when(SpringUtil.getBean(ConversionService.class)).thenReturn(conversionService);
//        Mockito.when(beanFactory.getBean(ConversionService.class)).thenReturn(conversionService);
        ReflectionTestUtils.setField(SpringUtil.class, "beanFactory", beanFactory);
        // Test case 1: value is null
        Integer result1 = ConvertUtil.convert(null, 10, Integer.class);
        Assertions.assertEquals(10, result1);

        // Test case 2: value is a JSON string
        String json = "{\"name\":\"John\",\"age\":30}";
        Person person = ConvertUtil.convert(json, null, Person.class);
        Assertions.assertNotNull(person);
        Assertions.assertEquals("John", person.getName());
        Assertions.assertEquals(30, person.getAge());

        // Test case 3: value is not a JSON string
        String stringValue = "123";
        Mockito.when(conversionService.convert("123", Integer.class)).thenReturn(123);
        Integer result3 = ConvertUtil.convert(stringValue, null, Integer.class);
        Assertions.assertEquals(123, result3);

        // Test case 4: value cannot be converted to the target type
        String invalidValue = "abc";
        Mockito.when(conversionService.convert("abc", Double.class)).thenReturn(1.0);
        Double result4 = ConvertUtil.convert(invalidValue, 1.0, Double.class);
        Assertions.assertEquals(1.0, result4);
    }

    @Data
    static class Person {

        private String name;

        private int age;

    }

}