package com.asli.shortener.url.api.exception;

public class ExpiredKeyException extends Exception {

    public ExpiredKeyException() {
        super("Shorten URL expired!");
    }

}
