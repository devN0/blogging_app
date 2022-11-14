package com.bloggingapp.bloggingapp.users.exceptions;

public class UserNotFoundException extends IllegalArgumentException {
    public UserNotFoundException(String userName) {
        super("No user found with username: "+userName);
    }
}
