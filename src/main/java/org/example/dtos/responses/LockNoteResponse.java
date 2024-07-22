package org.example.dtos.responses;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LockNoteResponse {

    private boolean LockStatus;
    private String email;
    private String message;
}
