package com.example.calorie.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/** The type Logger aspect cat. */
@Component
@Aspect
@Slf4j
public class LoggerAspect {
  /**
   * Log controller methods object.
   *
   * @param joinPoint the join point
   * @return the object
   * @throws Throwable the throwable
   */
  @Around("PointCuts.allMethodsFromController()")
  public Object logControllerMethods(ProceedingJoinPoint joinPoint) throws Throwable {
    String className = joinPoint.getTarget().getClass().getSimpleName();
    String methodName = joinPoint.getSignature().getName();

    log.info("Entering controller method: {}.{}", className, methodName);

    try {
      Object result = joinPoint.proceed();

      log.info("Exiting controller method: {}.{}", className, methodName);
      return result;
    } catch (Exception e) {
      log.error("Exception in controller method: {}.{}", className, methodName, e);
      throw e;
    }
  }

  /**
   * Log service methods object.
   *
   * @param joinPoint the join point
   * @return the object
   * @throws Throwable the throwable
   */
  @Around("PointCuts.allMethods()")
  public Object logServiceMethods(ProceedingJoinPoint joinPoint) throws Throwable {
    String className = joinPoint.getTarget().getClass().getSimpleName();
    String methodName = joinPoint.getSignature().getName();

    log.info("Entering service method: {}.{}", className, methodName);

    try {
      Object result = joinPoint.proceed();
      log.info("Exiting service method: {}.{}", className, methodName);
      return result;
    } catch (Exception e) {
      log.error("Exception in service method: {}.{}", className, methodName, e);
      throw e;
    }
  }
}
