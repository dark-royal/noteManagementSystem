package org.example.exceptions;

public class InvalidPasswordFormatException extends RuntimeException{
    public InvalidPasswordFormatException(String message){
        super(message);
    }
}
