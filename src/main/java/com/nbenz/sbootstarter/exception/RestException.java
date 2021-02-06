package com.nbenz.sbootstarter.exception;

public abstract class RestException extends Exception {

    public RestException(String message) {

        super(message);
    }
}
