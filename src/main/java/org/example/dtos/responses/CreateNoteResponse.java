package org.example.dtos.responses;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
public class CreateNoteResponse {
    private LocalDateTime dateCreated;
    private String title;
    private String content;
    private String email;
    @Id
    private String id;
    private String message;
}
