package org.electronic.store.ecommercestore.exceptions;

import org.electronic.store.ecommercestore.dtos.ApiResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseMessage> handleResourceNotFoundException(ResourceNotFoundException ex){
        ApiResponseMessage message = ApiResponseMessage.builder().message(ex.getMessage()).success(false).status(HttpStatus.BAD_REQUEST).build();
        logger.error(ex.getMessage());
        return ResponseEntity.badRequest().body(message);
    }

    public ResponseEntity<Map<String,Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        List<FieldError> errors = ex.getBindingResult().getFieldErrors();
        Map<String,Object> errorMap = new HashMap<>();
        errors.stream().forEach(error->{
            errorMap.put(error.getDefaultMessage(),error);
        });
        return ResponseEntity.badRequest().body(errorMap);
    }

    @ExceptionHandler(BadRApiRequest.class)
    public ResponseEntity<ApiResponseMessage> handleBadApiRequestException(BadRApiRequest ex){
        ApiResponseMessage message = ApiResponseMessage.builder().message(ex.getMessage()).success(false).status(HttpStatus.BAD_REQUEST).build();
        logger.error(ex.getMessage());
        return ResponseEntity.badRequest().body(message);
    }

}
