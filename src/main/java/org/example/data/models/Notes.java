package org.example.data.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class Notes {
    @Id
    private String id;
    private String title;
    private String email;
    private String content;
    private LocalDateTime dateCreated;
    private boolean lockStatus;
    private String password;
    @DBRef
    private Tags tags;
}
