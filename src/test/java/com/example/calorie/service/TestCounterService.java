package com.example.calorie.service;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CounterServiceTest {
    @Test
    void testDataAnnotation() {
        CounterService service = CounterService.getInstance();
        String toString = service.toString();
        boolean equals = service.equals(service);
        assertNotNull(toString);
        assertTrue(equals);
    }

    @Test
    void testGettersAndSetters() {
        CounterService service = CounterService.getInstance();

        int requestCount = service.getRequestCount();

        assertEquals(0, requestCount);
    }

    @Test
    void testIncrementRequestCount() {
        int initialCount = CounterService.getRequestCount();

        CounterService.incrementRequestCount();

        int newCount = CounterService.getRequestCount();
        assertEquals(initialCount + 1, newCount);
    }

    @Test
    void testGetRequestCount() {
        int initialCount = CounterService.getRequestCount();
        CounterService.incrementRequestCount();
        CounterService.incrementRequestCount();

        int currentCount = CounterService.getRequestCount();

        assertEquals(initialCount + 2, currentCount);
    }

    @Test
    void testSingleton() {
        CounterService service1 = null;
        CounterService service2 = null;

        service1 = CounterService.getInstance();
        service2 = CounterService.getInstance();

        assertEquals(service1, service2);
    }

    @Test
    void testHashCode() {
        CounterService service = CounterService.getInstance();
        int hashCode = service.hashCode();
        assertEquals(hashCode, service.hashCode());
    }

 

    @Test
    void testGetInstanceReturnsTheSameObject() {
        CounterService service1 = CounterService.getInstance();
        CounterService service2 = CounterService.getInstance();
        assertSame(service1, service2);
    }

    @Test
    void testGetInstanceReturnsNonNullObject() {
        CounterService service = CounterService.getInstance();
        assertNotNull(service);
    }
}
