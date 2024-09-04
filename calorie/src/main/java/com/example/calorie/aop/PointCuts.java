package com.example.calorie.aop;

import org.aspectj.lang.annotation.Pointcut;

/** The type Point cuts. */
public class PointCuts {
  /** All methods from controller. */
  @Pointcut(value = "execution(* com.example.calorie.controller.*.*(..)) ")
  public void allMethodsFromController() {}

  /** All methods. */
  @Pointcut(value = "execution(* com.example.calorie.service.*.*(..)) ")
  public void allMethods() {}
}
