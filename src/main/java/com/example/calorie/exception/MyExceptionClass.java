package com.example.calorie.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

/** The type My exception class. */
@ControllerAdvice
@Slf4j
public class MyExceptionClass {
  /**
   * Handle bad request exception response entity.
   *
   * @param e the e
   * @return the response entity
   */
  @ExceptionHandler({
    HttpMessageNotReadableException.class,
    BadRequestException.class,
    IllegalArgumentException.class,
    IllegalStateException.class
  })
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ResponseEntity<ApiError> handleBadRequestException(Exception e) {
    String errorMessage = "bad request: " + e.getMessage();
    log.error(errorMessage);
    ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST.value(), errorMessage);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
  }

  /**
   * Handle no handler found exception response entity.
   *
   * @param ex the ex
   * @return the response entity
   */
  @ExceptionHandler({ResourceNotFoundException.class, NoHandlerFoundException.class})
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ResponseBody
  public ResponseEntity<ApiError> handleNoHandlerFoundException(ResourceNotFoundException ex) {
    String errorMessage = "Ошибка запроса: " + ex.getMessage();
    ApiError apiError = new ApiError(HttpStatus.NOT_FOUND.value(), errorMessage);
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
  }

  /**
   * Handle exception response entity.
   *
   * @param e the e
   * @return the response entity
   */
  @ExceptionHandler({Exception.class, RuntimeException.class})
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ResponseBody
  public ResponseEntity<ApiError> handleException(Exception e) {
    String errorMessage;
    ApiError apiError = null;

    errorMessage = "Внутренняя ошибка сервера: " + e.getMessage();
    log.error(errorMessage);
    apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.value(), errorMessage);

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError);
  }
}
