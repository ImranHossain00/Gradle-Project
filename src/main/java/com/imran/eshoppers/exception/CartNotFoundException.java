package com.imran.eshoppers.exception;

public class CartNotFoundException extends RuntimeException{
    public CartNotFoundException(String msg) {
        super(msg);
    }
}
