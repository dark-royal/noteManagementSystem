package org.example.exceptions;

public class NoteDoesNotBelongToUserException extends RuntimeException {
    public NoteDoesNotBelongToUserException(String message) {
        super(message);
    }
}
