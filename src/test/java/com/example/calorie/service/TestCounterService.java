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
    void testIncrementRequestCountMultiThreaded() throws InterruptedException {
        int numThreads = 10;
        int incrementsPerThread = 1000;
        int expectedCount = numThreads * incrementsPerThread;

        Thread[] threads = new Thread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < incrementsPerThread; j++) {
                    CounterService.incrementRequestCount();
                }
            });
            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        int actualCount = CounterService.getRequestCount();
        assertEquals(expectedCount, actualCount);
    }
    @Test
    void testGetRequestCountMultiThreaded() throws InterruptedException {
        int numThreads = 10;
        int expectedCount = 1000;

        CounterService.incrementRequestCount();
        for (int i = 0; i < expectedCount - 1; i++) {
            new Thread(CounterService::incrementRequestCount).start();
        }

        Thread[] threads = new Thread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            threads[i] = new Thread(() -> {
                int count = CounterService.getRequestCount();
                assertEquals(expectedCount, count);
            });
            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }
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
}
