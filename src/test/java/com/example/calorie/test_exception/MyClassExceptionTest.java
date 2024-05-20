package com.example.calorie.test_exception;


import com.example.calorie.exception.ApiError;
import com.example.calorie.exception.MyExceptionClass;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class MyExceptionClassTest {

    
    @InjectMocks
    private MyExceptionClass myExceptionClass;
    
    @Test
    void testHandleNoHandlerFoundException() {
        ResourceNotFoundException exception = new ResourceNotFoundException("Resource not found");
        String expectedErrorMessage = "Ошибка запроса: " + exception.getMessage();

        ResponseEntity<ApiError> response = myExceptionClass.handleNoHandlerFoundException(exception);
        
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        ApiError apiError = response.getBody();
        assertNotNull(apiError);
        assertEquals(expectedErrorMessage, apiError.getErrorMessage());
    }

    @Test
    void testHandleException() {
        Exception exception = new Exception("Internal server error");
        String expectedErrorMessage = "Внутренняя ошибка сервера: " + exception.getMessage();

        ResponseEntity<ApiError> response = myExceptionClass.handleException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        ApiError apiError = response.getBody();
        assertNotNull(apiError);
        assertEquals(expectedErrorMessage, apiError.getErrorMessage());
    }
}
