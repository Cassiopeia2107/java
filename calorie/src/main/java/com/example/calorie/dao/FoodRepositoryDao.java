package com.example.calorie.dao;

import com.example.calorie.model.Food;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** The interface Food repository dao. */
@Repository
@Hidden
public interface FoodRepositoryDao extends JpaRepository<Food, Long> {}
