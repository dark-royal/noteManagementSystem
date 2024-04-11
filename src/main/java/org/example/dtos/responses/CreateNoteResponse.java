package org.example.dtos.responses;

import lombok.Data;

@Data
public class CreateNoteResponse {
    private String id;
    private String message;
}
