package org.example.exceptions;

public class NoteNotUpdatedException extends RuntimeException {
    public NoteNotUpdatedException(String message) {
        super(message);
    }
}
