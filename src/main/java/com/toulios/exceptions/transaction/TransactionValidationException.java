package com.toulios.exceptions.transaction;

public class TransactionValidationException extends RuntimeException {


    private static final long serialVersionUID = 830900483248644773L;

    public TransactionValidationException(String message){
        super(message);
    }
}
