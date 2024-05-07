package org.example.dtos.request;

import lombok.Data;

@Data
public class LockNoteRequest {

    private String password;
    private String email;
}
