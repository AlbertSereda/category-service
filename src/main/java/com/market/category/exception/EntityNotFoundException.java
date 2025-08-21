package com.market.category.exception;

import com.market.category.consts.ExceptionMessageType;

public class EntityNotFoundException extends BaseException {

    private EntityNotFoundException(ExceptionMessageType exceptionMessageType, String message) {
        super(exceptionMessageType, message);
    }

    public static EntityNotFoundException categoryNotFound(Long id) {
        ExceptionMessageType categoryNotFound = ExceptionMessageType.CATEGORY_NOT_FOUND;
        String message = String.format(categoryNotFound.getErrorMessage(), id);
        return new EntityNotFoundException(categoryNotFound, message);
    }

    public static EntityNotFoundException attributeGroupNotFound(Long id) {
        ExceptionMessageType groupNotFound = ExceptionMessageType.ATTRIBUTE_GROUP_NOT_FOUND;
        String message = String.format(groupNotFound.getErrorMessage(), id);
        return new EntityNotFoundException(groupNotFound, message);
    }

    public static EntityNotFoundException attributeNotFound(Long id) {
        ExceptionMessageType attributeNotFound = ExceptionMessageType.ATTRIBUTE_NOT_FOUND;
        String message = String.format(attributeNotFound.getErrorMessage(), id);
        return new EntityNotFoundException(attributeNotFound, message);
    }
}
