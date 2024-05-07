package org.example.dtos.responses;

import lombok.Data;

@Data
public class LockNoteResponse {

    private boolean LockStatus;
    private String email;
    private String message;
}
