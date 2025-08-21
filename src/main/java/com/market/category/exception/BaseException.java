package com.market.category.exception;

import com.market.category.consts.ExceptionMessageType;
import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {

    private final ExceptionMessageType type;

    public BaseException(ExceptionMessageType type) {
        super(type.getErrorMessage());
        this.type = type;
    }

    public BaseException(ExceptionMessageType type, String message) {
        super(message);
        this.type = type;
    }
}
