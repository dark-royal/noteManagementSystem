package org.example.dtos.responses;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class FindNoteResponse {
    private String message;
    private String title;
    private String content;
    private LocalDateTime dateCreated;
}
