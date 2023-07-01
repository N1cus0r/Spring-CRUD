package com.nicusor.SchoolApp.exceptions;

public class StudentNotFoundException extends EntityNotFoundException{
    public StudentNotFoundException(String message){
        super(message);
    }
}
