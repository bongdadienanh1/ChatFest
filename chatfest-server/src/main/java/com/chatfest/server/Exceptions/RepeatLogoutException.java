package com.chatfest.server.Exceptions;

public class RepeatLogoutException extends Exception {

    private static final long serialVersionUID = -1170122306562360223L;

    public RepeatLogoutException() {
        super("Your account has logged out!");
    }

    public RepeatLogoutException(String message) {
        super(message);
    }

    public RepeatLogoutException(String message, Throwable cause) {
        super(message, cause);
    }
}
