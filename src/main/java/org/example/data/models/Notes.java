package org.example.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Notes {
    @Id
    private String id;
    private String title;
    private String content;
    private User user;
    private LocalDateTime dateCreated;
    @DBRef
    private Tags tags;
}
