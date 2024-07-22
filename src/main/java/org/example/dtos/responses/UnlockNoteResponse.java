package org.example.dtos.responses;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UnlockNoteResponse {
    private String email;
    private String message;
    private boolean lockStatus;
}
