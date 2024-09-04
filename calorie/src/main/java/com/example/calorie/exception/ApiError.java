package com.example.calorie.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** The type Api error. */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {
  private String errorMessage;
  private Integer errorCode;

}
