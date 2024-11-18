package com.coddingshuttle.springbootweb.advices;

import com.coddingshuttle.springbootweb.exception.EmployeeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler extends RuntimeException{

@ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleEmployeeNotFound(EmployeeNotFoundException exception){
        ApiError apiError=ApiError.builder()
                .status(HttpStatus.NOT_FOUND)
                .message(exception.getMessage())
                .build();
      return   buildErrorResponseEntity(apiError);
        //return new ResponseEntity<>(apiError,HttpStatus.NOT_FOUND);
    }



    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleInternalServerException(Exception exception){
        ApiError apiError=ApiError.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(exception.getMessage())
                .build();
        //return new ResponseEntity<>(apiError,HttpStatus.INTERNAL_SERVER_ERROR);
        return   buildErrorResponseEntity(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception){

        List<String> errors= exception.getBindingResult().getAllErrors()
                .stream()
                .map(error-> error.getDefaultMessage())
                .toList();

        ApiError apiError=ApiError.builder()
        .status(HttpStatus.BAD_REQUEST)
        .message("Field input Validation failed")
        .subError(errors)
        .build();

        //return new ResponseEntity<>(apiError,HttpStatus.BAD_REQUEST);
        return   buildErrorResponseEntity(apiError);
    }

    private ResponseEntity<ApiResponse<?>> buildErrorResponseEntity(ApiError apiError) {

        return new ResponseEntity<>(new ApiResponse<>(apiError),apiError.getStatus());
    }


}
