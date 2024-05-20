package com.example.calorie.service;

import com.example.calorie.cache.LruCache;
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

  /**
   * Instantiates a new User service.
   *
   * @param userRepositoryDao the user repository dao
   */
  @Autowired
  public UserService(UserRepositoryDao userRepositoryDao) {
    this.userRepositoryDao = userRepositoryDao;
  }

  @Override
  public User addUser(User user) {
    if (user.getProducts() != null) {
      for (Food product : user.getProducts()) {
        product.setUser(user);
      }
    }
    User user1 = userRepositoryDao.save(user);
    cache.put(user1.getId(), user1);
    return user1;
  }

  @Override
  public User getUser(Long id) {
    if (cache.get(id) != null) {
      return cache.get(id);
    } else {
      return userRepositoryDao
          .findById(id)
          .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }
  }

  @Override
  public User updateUser(Long id, User newUser) {
    User user = getUser(id);
    user.setName(newUser.getName());
    if (newUser.getProducts() != null) {
      for (Food product : newUser.getProducts()) {
        product.setUser(user);
        user.addProduct(product);
      }
    }
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
  public List<User> getAllUsers() {
    return userRepositoryDao.findAll();
  }

  @Override
  public List<User> getAllUserByFood(String foodName) {
    return userRepositoryDao.findAllUserByFood(foodName);
  }
}
