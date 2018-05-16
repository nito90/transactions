package com.toulios.exceptions.account;

public class AccountValidationException extends RuntimeException{


    private static final long serialVersionUID = 2094003659069843793L;

    public AccountValidationException(String message) {
        super(message);
    }
}
