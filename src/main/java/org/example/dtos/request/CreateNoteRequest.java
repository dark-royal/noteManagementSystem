package org.example.dtos.request;

import lombok.Data;
import org.example.data.models.Tags;
import org.example.data.models.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CreateNoteRequest {
    @Id
    private String id;
    private String title;
    private String content;
    private LocalDateTime dateCreated;
    @DBRef
    private Tags tagName;
    private String email;


}
