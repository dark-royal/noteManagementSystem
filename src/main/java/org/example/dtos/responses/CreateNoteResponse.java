package org.example.dtos.responses;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Getter
@Setter
public class CreateNoteResponse {
    private LocalDateTime dateCreated;
    private String title;
    private String content;
    private String email;
    @Id
    private String id;
    private String message;
}
