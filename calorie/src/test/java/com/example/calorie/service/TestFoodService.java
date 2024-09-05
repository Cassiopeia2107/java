package com.example.calorie.service;

import com.example.calorie.dao.FoodRepositoryDao;
import com.example.calorie.model.Food;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TestFoodService {
  @Mock private FoodRepositoryDao foodRepositoryDao;

  @InjectMocks private FoodService foodService;

  private Food existingFood;

  @BeforeEach
  void setUp() {
    existingFood = new Food(1L, "Apple", 100);
  }
  @Test
  void testGetFood_ThrowsResourceNotFoundException() {
    Long id = 999L;
    when(foodRepositoryDao.findById(id)).thenReturn(Optional.empty());

    assertThrows(ResourceNotFoundException.class, () -> foodService.getFood(id));
    verify(foodRepositoryDao, times(1)).findById(id);
  }
  @Test
  void testUpdateFood_ThrowsResourceNotFoundException() {
    Long id = 999L;
    Food updatedFood = new Food(id, "Banana", 150);
    when(foodRepositoryDao.findById(id)).thenReturn(Optional.empty());

    assertThrows(ResourceNotFoundException.class, () -> foodService.updateFood(id, updatedFood));
    verify(foodRepositoryDao, times(1)).findById(id);
  }

  @Test
  void testGetAllFoods() {
    List<Food> foods = Arrays.asList(existingFood, new Food(2L, "Orange", 80));
    when(foodRepositoryDao.findAll()).thenReturn(foods);

    List<Food> result = foodService.getAllFoods();

    assertNotNull(result);
    assertEquals(foods, result);
    verify(foodRepositoryDao, times(1)).findAll();
  }

  @Test
  void testGetFood() {
    Long id = 1L;
    Food food = new Food(id, "Apple", 95);

    when(foodRepositoryDao.findById(id)).thenReturn(Optional.of(food));

    Food result = foodService.getFood(id);

    assertEquals(food, result);
    verify(foodRepositoryDao, times(1)).findById(id);
  }

  @Test
  void testUpdateFood() {
    Food updatedFood = new Food(1L, "Banana", 150);
    when(foodRepositoryDao.findById(1L)).thenReturn(Optional.of(existingFood));
    when(foodRepositoryDao.save(any(Food.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));

    Food result = foodService.updateFood(1L, updatedFood);

    assertNotNull(result);
    assertEquals(updatedFood.getName(), result.getName());
    assertEquals(updatedFood.getCalories(), result.getCalories());
    verify(foodRepositoryDao, times(1)).save(result);
  }

  @Test
  void testAddFood() {
    Food food = new Food(null, "Banana", 105);
    Food savedFood = new Food(1L, "Banana", 105);
    when(foodRepositoryDao.save(food)).thenReturn(savedFood);

    Food result = foodService.addFood(food);

    assertEquals(savedFood, result);
    verify(foodRepositoryDao, times(1)).save(food);
  }

  @Test
  void testDeleteFood() {
    when(foodRepositoryDao.findById(1L)).thenReturn(Optional.of(existingFood));
    doNothing().when(foodRepositoryDao).delete(existingFood);

    String result = foodService.deleteFood(1L);

    assertEquals("delete", result);
    verify(foodRepositoryDao, times(1)).findById(1L);
    verify(foodRepositoryDao, times(1)).delete(existingFood);
  }
}
