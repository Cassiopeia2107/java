package com.example.calorie.service;

import com.example.calorie.cache.LruCache;
import com.example.calorie.dao.FoodRepositoryDao;
import com.example.calorie.dao.UserRepositoryDao;
import com.example.calorie.model.Food;
import com.example.calorie.model.User;
import com.example.calorie.service.interfaces.UserInterface;
import io.swagger.v3.oas.annotations.Hidden;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

/** The type User service. */
@Service
@Hidden
public class UserService implements UserInterface {
  private final UserRepositoryDao userRepositoryDao;
  private final LruCache<Long, User> cache = new LruCache<>();
  private final FoodRepositoryDao foodRepositoryDao;

  /**
   * Instantiates a new User service.
   *
   * @param userRepositoryDao the user repository dao
   * @param foodRepositoryDao the food repository dao
   */
  @Autowired
  public UserService(UserRepositoryDao userRepositoryDao, FoodRepositoryDao foodRepositoryDao) {
    this.userRepositoryDao = userRepositoryDao;
    this.foodRepositoryDao = foodRepositoryDao;
  }

  @Override
  public User addUser(User user) {
    if (user.getProducts() != null) {
      for (Food product : user.getProducts()) {
        product.setUser(user);
      }
    }
    User savedUser = userRepositoryDao.save(user);
    cache.put(savedUser.getId(), savedUser);
    return savedUser;
  }

  @Override
  public User getUser(Long id) {
    User user = cache.get(id);
    if (user == null) {
      user =
          userRepositoryDao
              .findById(id)
              .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
      cache.put(id, user);
    }
    return user;
  }

  @Override
  public User updateUser(Long id, User newUser) {
    User user = getUser(id);
    user.setName(newUser.getName());
    if (newUser.getProducts() != null) {
      for (Food product : newUser.getProducts()) {
        boolean productExists =
            user.getProducts().stream()
                .anyMatch(
                    existingProduct ->
                        existingProduct.getName().equals(product.getName())
                            && existingProduct.getCalories() == product.getCalories());
        if (!productExists) {
          product.setUser(user);
          user.addProduct(product);
        }
      }
    }
    return userRepositoryDao.save(user);
  }

  @Override
  public User addFoodToUser(Long userId, Long foodId) {
    User user = getUser(userId);
    Food food =
        foodRepositoryDao.findById(foodId).orElseThrow(() -> new RuntimeException("not found"));
    user.addProduct(food);
    cache.put(userId, user);
    return userRepositoryDao.save(user);
  }

  @Override
  public String deleteUser(Long id) {
    User user = getUser(id);

    if (user.getProducts() != null) {
      for (Food product : user.getProducts()) {
        product.setUser(null);
      }
      user.setProducts(null);
    }
    cache.remove(id);
    userRepositoryDao.delete(user);
    return "delete";
  }

  @Override
  public String deleteFoodByUser(Long userId, Long foodId) {
    User user =
        userRepositoryDao
            .findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("user not found"));
    Food food =
        foodRepositoryDao
            .findById(foodId)
            .orElseThrow(() -> new ResourceNotFoundException("Food not found"));

    if (!food.getUser().getId().equals(userId)) {
      throw new IllegalArgumentException("Food does not belong to user");
    }
    food.setUser(null);
    user.removeProduct(food);
    cache.put(userId, user);
    userRepositoryDao.save(user);
    return "delete";
  }

  @Override
  public List<User> getAllUsers() {
    return userRepositoryDao.findAll();
  }

  @Override
  public List<User> getAllUserByFood(String foodName) {
    List<User> users = userRepositoryDao.findAllUserByFood(foodName);
    if (users == null || users.isEmpty()) {
      throw new ResourceNotFoundException("not found users with food :" + foodName);
    }
    return users;
  }

  // 5 лаба bulk операция с сокращенной формой лямбда выражения
  @Override
  public List<User> addUsers(List<User> users) {
    return users.stream().map(this::addUser).toList();
  }
}
