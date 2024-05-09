package org.example.dtos.responses;

import lombok.Data;

@Data
public class UnlockNoteResponse {
    private String email;
    private String message;
    private boolean lockStatus;
}
