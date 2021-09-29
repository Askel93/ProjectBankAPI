package com.example.project.bank.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(String entityName, int id) {
        super("Entity '" + entityName + "' with id = " + id + " is not found");
    }
    public EntityNotFoundException(String entityName, String value) {
        super("Entity '" + entityName + "' with value = " + value + " is not found");
    }
}