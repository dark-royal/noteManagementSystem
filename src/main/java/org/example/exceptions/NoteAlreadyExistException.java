package org.example.exceptions;

public class NoteAlreadyExistException extends RuntimeException{
    public NoteAlreadyExistException(String message){
        super(message);
    }
}
