package com.example.calorie.exception;

import io.micrometer.common.lang.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/** The type My exception class. */
@ControllerAdvice
@Slf4j
public class MyExceptionClass extends ResponseEntityExceptionHandler {

  @Nullable
  @Override
  protected ResponseEntity<Object> handleExceptionInternal(
      Exception ex,
      @Nullable Object body,
      HttpHeaders headers,
      HttpStatusCode statusCode,
      WebRequest request) {
    if (statusCode.equals(HttpStatus.NOT_FOUND)) {
      ApiError errorResponse =
          new ApiError(
              "Resource Not Found Error: " + ex.getMessage() + " Are you lost, baby girl?😘 ;)",
              statusCode.value());
      return new ResponseEntity<>(errorResponse, headers, statusCode);
    } else if (statusCode.equals(HttpStatus.BAD_REQUEST)) {
      ApiError errorResponse =
          new ApiError(
              "Bad Request Error: "
                  + ex.getMessage()
                  + " Your behavior is obscene! You better fix it, baby!😈 :)",
              statusCode.value());
      return new ResponseEntity<>(errorResponse, headers, statusCode);
    } else if (statusCode.equals(HttpStatus.INTERNAL_SERVER_ERROR)) {
      ApiError errorResponse =
          new ApiError(
              "Internal Server Error: "
                  + ex.getMessage()
                  + " Sorry, I'm just a piece of metal🤕 :(",
              statusCode.value());
      return new ResponseEntity<>(errorResponse, headers, statusCode);
    }
    return new ResponseEntity<>(body, headers, statusCode);
  }

  /**
   * Handle internal server error exception response entity.
   *
   * @param ex the ex
   * @return the response entity
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiError> handleInternalServerErrorException(Exception ex) {
    ApiError errorResponse =
        new ApiError(
            "Internal Server Error: " + ex.getMessage() + " Sorry, I'm just a piece of metal🤕 :(",
            HttpStatus.INTERNAL_SERVER_ERROR.value());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
  }
}
