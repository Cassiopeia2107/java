package com.example.calorie.controller;

import com.example.calorie.model.Food;
import com.example.calorie.service.FoodService;
import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** The type Food controller. */
@RestController
@RequestMapping("/api/v1")
@CrossOrigin("*")
public class FoodController {

  private final FoodService foodService;

  /**
   * Instantiates a new Food controller.
   *
   * @param foodService the food service
   */
  public FoodController(FoodService foodService) {
    this.foodService = foodService;
  }

  /**
   * Add food food.
   *
   * @param food the food
   * @return the food
   */
  @PostMapping
  public Food addFood(@RequestBody Food food) {
    return foodService.addFood(food);
  }

  /**
   * Gets foods.
   *
   * @return the foods
   */
  @GetMapping("/foods")
  public List<Food> getFoods() {
    return foodService.getAllFoods();
  }

  /**
   * Gets food.
   *
   * @param id the id
   * @return the food
   */
  @GetMapping("/food/{id}")
  public Food getFood(@PathVariable Long id) {
    return foodService.getFood(id);
  }

  /**
   * Update food food.
   *
   * @param id the id
   * @param newFood the new food
   * @return the food
   */
  @PutMapping("/food/{id}")
  public Food updateFood(@PathVariable Long id, @RequestBody Food newFood) {
    return foodService.updateFood(id, newFood);
  }

  /**
   * Delete food.
   *
   * @param id the id
   */
  @DeleteMapping("/delete/food/{id}")
  public void deleteFood(@PathVariable Long id) {
    foodService.deleteFood(id);
  }
}
