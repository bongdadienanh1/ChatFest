package com.chatfest.server.Exceptions;

public class RepeatLoginException extends Exception {

    private static final long serialVersionUID = 5183001384087222695L;

    public RepeatLoginException() {
        super("Can't repeat login!");
    }

    public RepeatLoginException(String message) {
        super(message);
    }

    public RepeatLoginException(String message, Throwable cause) {
        super(message, cause);
    }
}
