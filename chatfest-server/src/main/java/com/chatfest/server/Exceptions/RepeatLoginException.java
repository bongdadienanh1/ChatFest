package com.chatfest.server.Exceptions;

public class RepeatLoginException extends Exception {

    private static final long serialVersionUID = 5183001384087222695L;

    public RepeatLoginException() {
        super("Your account has logged in!");
    }

    public RepeatLoginException(String message) {
        super(message);
    }

    public RepeatLoginException(String message, Throwable cause) {
        super(message, cause);
    }
}
