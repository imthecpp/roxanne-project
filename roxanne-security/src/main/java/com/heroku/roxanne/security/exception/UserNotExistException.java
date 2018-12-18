package com.heroku.roxanne.security.exception;

public class UserNotExistException extends Exception {

    public UserNotExistException(String message) {
        super(message);
    }
}
