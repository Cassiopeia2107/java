package com.example.calorie.model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class FoodTest {
    private Food food;

    @Mock
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        food = new Food("Apple", 95);
    }

    @Test
    void testGetName() {
        assertEquals("Apple", food.getName());
    }

    @Test
    void testGetCalories() {
        assertEquals(95, food.getCalories());
    }

    @Test
    void testSetName() {
        food.setName("Banana");
        assertEquals("Banana", food.getName());
    }

    @Test
    void testSetCalories() {
        food.setCalories(120);
        assertEquals(120, food.getCalories());
    }

    @Test
    void testGetUser() {
        when(user.getId()).thenReturn(1L);
        food.setUser(user);
        assertEquals(user, food.getUser());
    }
}
