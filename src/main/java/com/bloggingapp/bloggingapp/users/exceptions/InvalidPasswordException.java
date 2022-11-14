package com.bloggingapp.bloggingapp.users.exceptions;

public class InvalidPasswordException extends IllegalArgumentException {
    public InvalidPasswordException() {
        super("given password is invalid");
    }
}
