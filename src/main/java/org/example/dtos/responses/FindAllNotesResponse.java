package org.example.dtos.responses;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class FindAllNotesResponse {
    private String title;
    private String content;
    private LocalDateTime dateCreated;
    private String id;
}
