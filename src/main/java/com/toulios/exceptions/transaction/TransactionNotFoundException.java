package com.toulios.exceptions.transaction;

public class TransactionNotFoundException extends RuntimeException {


    private static final long serialVersionUID = -1105743818905688811L;

    public TransactionNotFoundException(String message) {
        super(message);
    }
}
