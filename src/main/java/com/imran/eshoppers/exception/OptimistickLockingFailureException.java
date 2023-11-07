package com.imran.eshoppers.exception;

public class OptimistickLockingFailureException extends RuntimeException{
    public OptimistickLockingFailureException(String msg) {
        super(msg);
    }
}
