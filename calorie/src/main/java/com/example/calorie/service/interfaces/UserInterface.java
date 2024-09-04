package com.example.calorie.service.interfaces;

import com.example.calorie.model.User;
import io.swagger.v3.oas.annotations.Hidden;
import java.util.List;

/** The interface User interface. */
@Hidden
public interface UserInterface {

  /**
   * Add user user.
   *
   * @param user the user
   * @return the user
   */
  User addUser(User user);

  /**
   * Gets user.
   *
   * @param id the id
   * @return the user
   */
  User getUser(Long id);

  /**
   * Update user user.
   *
   * @param id the id
   * @param newUser the new user
   * @return the user
   */
  User updateUser(Long id, User newUser);

  /**
   * Delete user string.
   *
   * @param id the id
   * @return the string
   */
  String deleteUser(Long id);

  /**
   * Gets all users.
   *
   * @return the all users
   */
  List<User> getAllUsers();

  /**
   * Gets all user by food.
   *
   * @param foodName the food name
   * @return the all user by food
   */
  List<User> getAllUserByFood(String foodName);

  /**
   * Add users list.
   *
   * @param users the users
   * @return the list
   */
  List<User> addUsers(List<User> users);

  /**
   * Delete food by user string.
   *
   * @param userId the user id
   * @param foodId the food id
   * @return the string
   */
  String deleteFoodByUser(Long userId, Long foodId);

  /**
   * Add food to user user.
   *
   * @param userId the user id
   * @param foodId the food id
   * @return the user
   */
  User addFoodToUser(Long userId, Long foodId);
}
