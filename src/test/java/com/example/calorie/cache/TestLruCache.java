package com.example.calorie.cache;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LruCacheTest {

    private LruCache<String, Integer> lruCache;

    @BeforeEach
    void setUp() {
        lruCache = new LruCache<>();
    }

    @Test
    void testPutAndGet() {
        lruCache.put("key1", 1);
        lruCache.put("key2", 2);
        lruCache.put("key3", 3);

        assertEquals(1, lruCache.get("key1"));
        assertEquals(2, lruCache.get("key2"));
        assertEquals(3, lruCache.get("key3"));
    }

    @Test
    void testEvictionOnPut() {
        lruCache.put("key1", 1);
        lruCache.put("key2", 2);
        lruCache.put("key3", 3);
        lruCache.put("key4", 4);

        assertEquals(2, lruCache.get("key2"));
        assertEquals(3, lruCache.get("key3"));
        assertEquals(4, lruCache.get("key4"));
    }

    @Test
    void testGetOnMiss() {
        lruCache.put("key1", 1);
        lruCache.put("key2", 2);

        assertNull(lruCache.get("key3"));
    }

    @Test
    void testRemove() {
        lruCache.put("key1", 1);
        lruCache.put("key2", 2);
        lruCache.put("key3", 3);

        lruCache.remove("key2");

        assertNull(lruCache.get("key2"));
        assertEquals(1, lruCache.get("key1"));
        assertEquals(3, lruCache.get("key3"));
    }
}