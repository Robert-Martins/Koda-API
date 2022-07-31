package com.robertmartins.notesapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ActionNotAllowedException extends RuntimeException {

    public ActionNotAllowedException(){
    }

}
