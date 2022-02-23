package com.registerWithJWT.registerWithJWT.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class InvalidInputException extends Exception{
    private List<String> attributeNames;

    public InvalidInputException() {

    }
}
