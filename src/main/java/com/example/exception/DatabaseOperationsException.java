package com.example.exception;

public class DatabaseOperationsException extends Exception {

    // Constructor with a message
    public DatabaseOperationsException(String message) {
        super(message);
    }

    // Constructor with a message and a cause (e.g., SQLException)
    public DatabaseOperationsException(String message, Throwable cause) {
        super(message, cause);
    }
}