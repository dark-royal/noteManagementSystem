package org.example.dtos.responses;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FindNoteResponse {
    private String message;
    private String title;
    private String content;
    private LocalDateTime dateCreated;
}
