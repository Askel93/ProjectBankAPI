package com.example.project.bank.api;

import com.example.project.bank.api.exception.BankCardGenerateException;
import com.example.project.bank.api.exception.EntityNotFoundException;
import com.example.project.bank.api.dto.ErrorDTO;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestApiExceptionHandler extends ResponseEntityExceptionHandler{

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request){

        ErrorDTO errorDTO = new ErrorDTO(1, ex.getMessage());
        return handleExceptionInternal(ex, errorDTO,
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(PersistenceException.class)
    protected ResponseEntity<Object> handlePersistenceException(PersistenceException ex, WebRequest request){

        ErrorDTO errorDTO = new ErrorDTO(2, ex.getMessage());
        return handleExceptionInternal(ex, errorDTO,
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(BankCardGenerateException.class)
    protected ResponseEntity<Object> handleBankCardGenerateException(BankCardGenerateException ex, WebRequest request){

        ErrorDTO errorDTO = new ErrorDTO(3, ex.getMessage());
        return handleExceptionInternal(ex, errorDTO,
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {

        ErrorDTO errorDTO = new ErrorDTO(5, ex.getMessage());
        return new ResponseEntity<>(errorDTO, status);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        ErrorDTO errorDTO = new ErrorDTO(6, errors.toString());
        return new ResponseEntity<>(errorDTO, status);
    }


    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {

        ErrorDTO errorDTO = new ErrorDTO(7, ex.getMessage());
        return new ResponseEntity<>(errorDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}