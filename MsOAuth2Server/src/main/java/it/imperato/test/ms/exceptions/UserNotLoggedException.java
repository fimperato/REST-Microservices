package it.imperato.test.ms.exceptions;

public class UserNotLoggedException extends Exception {

    public UserNotLoggedException(String errorMessage) {
        super(errorMessage);
    }

}