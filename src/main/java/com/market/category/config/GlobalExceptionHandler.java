package com.market.category.config;

import com.market.category.consts.ExceptionMessageType;
import com.market.category.dto.ResponseObjectDto;
import com.market.category.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ResponseObjectDto<Void>> handleBaseException(BaseException e) {
        ExceptionMessageType type = e.getType();
        log.error("Ошибка {}: {}", type, e.getMessage());
        HttpStatus httpStatus = type.getHttpStatus();
        return ResponseEntity.status(httpStatus)
                             .body(ResponseObjectDto.error(e.getMessage(), httpStatus));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseObjectDto<Void>> handleBaseException(MethodArgumentNotValidException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        Set<String> errors = e.getBindingResult()
                              .getAllErrors()
                              .stream()
                              .map(DefaultMessageSourceResolvable::getDefaultMessage)
                              .collect(Collectors.toSet());
        return ResponseEntity.status(status)
                             .body(ResponseObjectDto.error(errors, status));
    }

    @ExceptionHandler
    public ResponseEntity<ResponseObjectDto<Void>> handleUnknownException(Exception e) {
        log.error("Не обработанная ошибка: {}", e.getMessage(), e);
        HttpStatus internalServerError = HttpStatus.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(internalServerError)
                             .body(ResponseObjectDto.error(internalServerError));
    }
}
