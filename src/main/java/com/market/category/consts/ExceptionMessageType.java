package com.market.category.consts;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionMessageType {

    ENTITY_NOT_FOUND("Не найден объект с данным ID: %s", HttpStatus.NOT_FOUND),
    ATTRIBUTE_GROUP_NOT_FOUND("Не найдена группа атрибутов с ID: %s", HttpStatus.NOT_FOUND),
    ATTRIBUTE_NOT_FOUND("Не найден атрибут с ID: %s", HttpStatus.NOT_FOUND),
    CATEGORY_NOT_FOUND("Не найдена категория с ID: %s", HttpStatus.NOT_FOUND);


    private final String errorMessage;

    private final HttpStatus httpStatus;

    ExceptionMessageType(String errorMessage, HttpStatus httpStatus) {
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }
}
