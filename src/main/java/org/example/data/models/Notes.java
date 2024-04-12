package org.example.data.models;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Notes {
    private String id;
    private String title;
    private String content;
    private LocalDateTime dateAndTimeCreated;
    private List<Tags> tagsList;
}
