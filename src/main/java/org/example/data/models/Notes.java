package org.example.data.models;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class Notes {
    private String id;
    private String title;
    private String content;
    private LocalDateTime dateAndTimeCreated;
}
