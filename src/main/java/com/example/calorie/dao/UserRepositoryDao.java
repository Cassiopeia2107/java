package com.example.calorie.dao;

import com.example.calorie.model.User;
import io.swagger.v3.oas.annotations.Hidden;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/** The interface User repository dao. */
@Repository
@Hidden
public interface UserRepositoryDao extends JpaRepository<User, Long> {
  /**
   * Find all user by food list.
   *
   * @param foodName the food name
   * @return the list
   */
  @Query("SELECT DISTINCT u FROM User u JOIN u.products p WHERE p.name = :foodName")
  List<User> findAllUserByFood(@Param("foodName") String foodName);
}
