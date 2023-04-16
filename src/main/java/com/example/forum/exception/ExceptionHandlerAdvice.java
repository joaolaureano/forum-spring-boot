package com.example.forum.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ExceptionHandlerAdvice {


    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionDetails> handlerEntityNotFoundExceptionException(EntityNotFoundException ex) {
        return new ResponseEntity<>(
                ExceptionDetails.builder()
                        .title("Entidade não encontrada")
                        .message(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build(),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MappedEntityNotFoundException.class)
    public ResponseEntity<ExceptionDetails> handlerMappedEntityNotFoundExceptionException(MappedEntityNotFoundException ex) {
        return new ResponseEntity<>(
                ExceptionDetails.builder()
                        .title("Entidade mapeada não encontrada")
                        .message(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build(),
                HttpStatus.NOT_FOUND);
    }
}
