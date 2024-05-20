package com.example.calorie.controller;

import com.example.calorie.model.User;
import com.example.calorie.service.UserService;
import java.util.List;
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
  @PostMapping("/add-user")
  public User addUser(@RequestBody User user) {
    return userService.addUser(user);
  }

  /**
   * Gets user.
   *
   * @param id the id
   * @return the user
   */
  @GetMapping("/get-user/{id}")
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
  @PutMapping("/update-user/{id}")
  public User updateUser(@PathVariable Long id, @RequestBody User user) {
    return userService.updateUser(id, user);
  }

  /**
   * Delete user string.
   *
   * @param id the id
   * @return the string
   */
  @DeleteMapping("/delete-user/{id}")
  public String deleteUser(@PathVariable Long id) {
    return userService.deleteUser(id);
  }

  /**
   * Gets all users.
   *
   * @return the all users
   */
  @GetMapping("/get-users")
  public List<User> getAllUsers() {
    return userService.getAllUsers();
  }

  /**
   * Gets users by id.
   *
   * @param foodName the food name
   * @return the users by id
   */
  @GetMapping("/{foodName}")
  public List<User> getUsersByFood(@PathVariable String foodName) {
    return userService.getAllUserByFood(foodName);
  }
}
