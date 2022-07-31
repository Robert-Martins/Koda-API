package com.robertmartins.notesapi.handler;

import com.robertmartins.notesapi.exceptions.ActionNotAllowedException;
import com.robertmartins.notesapi.exceptions.DuplicateKeyException;
import com.robertmartins.notesapi.exceptions.ExceptionDetails;
import com.robertmartins.notesapi.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionDetails> handleResourceNotFoundException(ResourceNotFoundException ex){
        return new ResponseEntity<>(
                ExceptionDetails.builder()
                        .title("Resource Not Found")
                        .status(HttpStatus.NOT_FOUND.value())
                        .details(ex.getMessage())
                        .developerMessage(ex.getClass().getName())
                        .timestamp(LocalDateTime.now())
                        .build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ActionNotAllowedException.class)
    public ResponseEntity<ExceptionDetails> handleActionNotAllowedException(ActionNotAllowedException ex){
        return new ResponseEntity<>(
                ExceptionDetails.builder()
                        .title("Action Not Allowed")
                        .status(HttpStatus.FORBIDDEN.value())
                        .details("Action Not Allowed")
                        .developerMessage(ex.getClass().getName())
                        .timestamp(LocalDateTime.now())
                        .build(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<ExceptionDetails> handleDuplicateKeyException(DuplicateKeyException ex){
        return new ResponseEntity<>(
                ExceptionDetails.builder()
                        .title("Duplicate Key")
                        .status(HttpStatus.CONFLICT.value())
                        .details(ex.getMessage())
                        .developerMessage(ex.getClass().getName())
                        .timestamp(LocalDateTime.now())
                        .build(), HttpStatus.CONFLICT);
    }

}
