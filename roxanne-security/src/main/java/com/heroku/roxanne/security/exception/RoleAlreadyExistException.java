package com.heroku.roxanne.security.exception;

public class RoleAlreadyExistException extends Exception {

    public RoleAlreadyExistException(String message) {
        super(message);
    }
}
