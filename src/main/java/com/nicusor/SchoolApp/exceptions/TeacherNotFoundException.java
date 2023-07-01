package com.nicusor.SchoolApp.exceptions;

public class TeacherNotFoundException extends EntityNotFoundException{
    public TeacherNotFoundException(String message) {
        super(message);
    }
}
