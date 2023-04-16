package com.example.forum.exception;

public class MappedEntityNotFoundException extends RuntimeException{

    public MappedEntityNotFoundException(String originalEntity, String mappedEntity, Integer mappedEntityId){
        super(String.format(ExceptionEnum.MAPPED_ENTITY_NOT_FOUND.getMessage(),originalEntity, mappedEntity, mappedEntityId));
    }
}
