package com.example.forum.exception;

public enum ExceptionEnum {

    ENTITY_NOT_FOUND("Entity %s not found"),
    MAPPED_ENTITY_NOT_FOUND("Entity %s required %s identified by ID %d, but it does not exist");

    String message;

    ExceptionEnum(String message) {
        this.message = message;
    }

    protected String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return message;
    }
}
