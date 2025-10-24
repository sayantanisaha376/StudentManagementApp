package com.example.exception;


import com.example.entity.Student;

public class StudentNotFoundException extends Exception {

    // Constructor with a message
    public StudentNotFoundException(String message) {
        super(message);
    }
}
