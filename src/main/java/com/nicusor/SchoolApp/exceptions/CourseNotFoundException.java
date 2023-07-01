package com.nicusor.SchoolApp.exceptions;

public class CourseNotFoundException extends EntityNotFoundException{
    public CourseNotFoundException(String message) {
        super(message);
    }
}
