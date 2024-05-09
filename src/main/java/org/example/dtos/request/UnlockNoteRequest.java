package org.example.dtos.request;

import lombok.Data;

@Data
public class UnlockNoteRequest {
    private String email;
    private String password;
}
