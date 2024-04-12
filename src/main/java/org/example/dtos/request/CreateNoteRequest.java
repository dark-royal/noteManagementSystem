package org.example.dtos.request;

import lombok.Data;
import org.example.data.models.Tags;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CreateNoteRequest {

    private String title;
    private String content;
    private LocalDateTime dateCreated;
    private List<Tags>tagsList;
}
