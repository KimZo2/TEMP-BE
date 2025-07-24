package com.KimZo2.Back.exception;

public class DuplicateUserNickNameException extends RuntimeException {
    public DuplicateUserNickNameException(String message) {
        super(message);
    }
}
