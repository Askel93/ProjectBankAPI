package com.example.project.bank.api.exception;

public class BankCardGenerateException extends RuntimeException{
    public BankCardGenerateException(int countOfAttempts) {
        super("Failed to generate bank card number in " + countOfAttempts + " attempts");
    }
}