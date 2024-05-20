package com.example.calorie;

import com.example.calorie.cache.LruCache;
import com.example.calorie.dao.FoodRepositoryDao;
import com.example.calorie.interfaces.FoodInterface;
import com.example.calorie.model.Food;
import io.swagger.v3.oas.annotations.Hidden;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

/** The type Food service. */
@Service
@Hidden
public class FoodService implements FoodInterface {

  private final FoodRepositoryDao foodRepositoryDao;
  private final LruCache<Long, Food> cache = new LruCache<>();

  /**
   * Instantiates a new Food service.
   *
   * @param foodRepositoryDao the food repository dao
   */
  @Autowired
  public FoodService(FoodRepositoryDao foodRepositoryDao) {
    this.foodRepositoryDao = foodRepositoryDao;
  }

  @Override
  public Food getFood(Long id) {
    if (cache.get(id) != null) {
      return cache.get(id);
    } else {
      return foodRepositoryDao
          .findById(id)
          .orElseThrow(() -> new ResourceNotFoundException("food not found with id: " + id));
    }
  }

  @Override
  public Food addFood(Food food) {
    Food food1 = foodRepositoryDao.save(food);
    cache.put(food1.getId(), food);
    return food1;
  }

  @Override
  public Food updateFood(Long id, Food newFood) {
    Food food = getFood(id);
    food.setName(newFood.getName());
    food.setCalories(newFood.getCalories());
    return foodRepositoryDao.save(food);
  }

  @Override
  public List<Food> getAllFoods() {
    return foodRepositoryDao.findAll();
  }

  @Override
  public String deleteFood(Long id) {
    cache.remove(id);
    foodRepositoryDao.deleteById(id);
    return "delete";
  }
}
