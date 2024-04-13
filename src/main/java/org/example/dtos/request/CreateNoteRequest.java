package org.example.dtos.request;

import lombok.Data;
import org.example.data.models.Tags;
import org.example.data.models.User;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CreateNoteRequest {

    private String title;
    private String content;
    private LocalDateTime dateCreated;
    private Tags tagName;
    private String email;


}
