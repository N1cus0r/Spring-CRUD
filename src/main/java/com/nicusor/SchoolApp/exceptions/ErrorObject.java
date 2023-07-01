package com.nicusor.SchoolApp.exceptions;

import lombok.Data;

@Data
public class ErrorObject {
    private Integer statusCode;
    private String message;
}
