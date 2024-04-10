package org.example.dtos.request;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class CreateNoteRequest {

    private String title;
    private String content;
    private LocalDateTime dateCreated;

}
