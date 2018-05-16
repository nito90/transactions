package com.toulios.exceptions.account;

public class AccountNotFoundException extends RuntimeException {


    private static final long serialVersionUID = 537212340393857583L;

    public AccountNotFoundException(String message) {
        super(message);
    }

}
