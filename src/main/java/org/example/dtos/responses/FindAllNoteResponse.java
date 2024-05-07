package org.example.dtos.responses;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
public class FindAllNoteResponse {
    @Id
    private String id;
    private String email;
    private String title;
    private LocalDateTime dateCreated;
    private String content;
    private boolean lockStatus;

}
