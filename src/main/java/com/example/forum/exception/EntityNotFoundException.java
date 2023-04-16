package com.example.forum.exception;

public class EntityNotFoundException extends RuntimeException{

    public EntityNotFoundException(String entityName){
        super(String.format(ExceptionEnum.ENTITY_NOT_FOUND.getMessage(),entityName));
    }
}
