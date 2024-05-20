package com.example.calorie.service.interfaces;

import com.example.calorie.model.Food;
import io.swagger.v3.oas.annotations.Hidden;
import java.util.List;

/** The interface Food interface. */
@Hidden
public interface FoodInterface {

  /**
   * Add food food.
   *
   * @param food the food
   * @return the food
   */
  Food addFood(Food food);

  /**
   * Gets food.
   *
   * @param id the id
   * @return the food
   */
  Food getFood(Long id);

  /**
   * Delete food string.
   *
   * @param id the id
   * @return the string
   */
  String deleteFood(Long id);

  /**
   * Update food food.
   *
   * @param id the id
   * @param newFood the new food
   * @return the food
   */
  Food updateFood(Long id, Food newFood);

  /**
   * Gets all foods.
   *
   * @return the all foods
   */
  List<Food> getAllFoods();
}
