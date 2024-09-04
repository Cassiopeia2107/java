package com.example.calorie.controller;

import com.example.calorie.model.User;
import com.example.calorie.service.UserService;
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



/** The type User controller. */
@RestController
@RequestMapping("/api/v1")
@CrossOrigin("*")
public class UserController {

  private final UserService userService;

  /**
   * Instantiates a new User controller.
   *
   * @param userService the user service
   */
  public UserController(UserService userService) {
    this.userService = userService;
  }

  /**
   * Add u ser user.
   *
   * @param user the user
   * @return the user
   */
  @PostMapping("/user")
  public User addUser(@RequestBody User user) {
    return userService.addUser(user);
  }

  /**
   * Add users list.
   *
   * @param users the users
   * @return the list
   */
  @PostMapping("/users")
  public List<User> addUsers(@RequestBody List<User> users) {
    return userService.addUsers(users);
  }

  /**
   * Add food to u ser user.
   *
   * @param foodId the food id
   * @param userId the user id
   * @return the user
   */
  @PostMapping("/user/{userId}/food/{foodId}")
  public User addFoodToUser(@PathVariable Long foodId, @PathVariable Long userId) {
    return userService.addFoodToUser(userId, foodId);
  }

  /**
   * Gets user.
   *
   * @param id the id
   * @return the user
   */
  @GetMapping("/user/{id}")
  public User getUser(@PathVariable Long id) {
    return userService.getUser(id);
  }

  /**
   * Update user user.
   *
   * @param id the id
   * @param user the user
   * @return the user
   */
  @PutMapping("/user/{id}")
  public User updateUser(@PathVariable Long id, @RequestBody User user) {
    return userService.updateUser(id, user);
  }

  /**
   * Delete user.
   *
   * @param id the id
   */
  @DeleteMapping("/user/{id}")
  public void deleteUser(@PathVariable Long id) {
    userService.deleteUser(id);
  }

  /**
   * Delete food by user string.
   *
   * @param userId the user id
   * @param foodId the food id
   * @return the string
   */
  @DeleteMapping("/user/{userId}/food/{foodId}")
  public String deleteFoodByUser(@PathVariable Long userId, @PathVariable Long foodId) {
    return userService.deleteFoodByUser(userId, foodId);
  }

  /**
   * Gets all users.
   *
   * @return the all users
   */
  @GetMapping("/users")
  public List<User> getAllUsers() {
    return userService.getAllUsers();
  }

  /**
   * Gets users by id.
   *
   * @param foodName the food name
   * @return the users by id
   */
  @GetMapping("/users/{foodName}")
  public List<User> getUsersByFood(@PathVariable String foodName) {
    return userService.getAllUserByFood(foodName);
  }
}
