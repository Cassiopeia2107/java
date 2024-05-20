package com.example.calorie.controller;

import com.example.calorie.model.Food;
import com.example.calorie.service.FoodService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class FoodControllerTest {

    @Mock
    private FoodService foodService;

    @InjectMocks
    private FoodController foodController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddFood() {
        Food food = new Food("Apple", 95);
        when(foodService.addFood(food)).thenReturn(food);

        Food result = foodController.addFood(food);

        assertEquals(food, result);
        verify(foodService, times(1)).addFood(food);
    }

    @Test
    void testGetFoods() {
        List<Food> foods = new ArrayList<>();
        foods.add(new Food("Apple", 95));
        foods.add(new Food("Banana", 105));
        when(foodService.getAllFoods()).thenReturn(foods);

        List<Food> result = foodController.getFoods();

        assertEquals(foods, result);
        verify(foodService, times(1)).getAllFoods();
    }

    @Test
    void testGetFood() {
        Food food = new Food("Apple", 95);
        when(foodService.getFood(1L)).thenReturn(food);

        Food result = foodController.getFood(1L);

        assertEquals(food, result);
        verify(foodService, times(1)).getFood(1L);
    }

    @Test
    void testUpdateFood() {
        Food newFood = new Food("Banana", 105);
        when(foodService.updateFood(1L, newFood)).thenReturn(newFood);

        Food result = foodController.updateFood(1L, newFood);

        assertEquals(newFood, result);
        verify(foodService, times(1)).updateFood(1L, newFood);
    }

    @Test
    void testDeleteFood() {
        when(foodService.deleteFood(1L)).thenReturn("Food deleted successfully");

        String result = foodController.deleteFood(1L);

        assertEquals("Food deleted successfully", result);
        verify(foodService, times(1)).deleteFood(1L);
    }
}
