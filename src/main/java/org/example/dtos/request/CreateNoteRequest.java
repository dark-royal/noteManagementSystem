package org.example.dtos.request;


import lombok.Data;
import org.example.data.models.Tags;
import org.springframework.data.annotation.Id;


import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
public class CreateNoteRequest {
   @Id
    private String id;
    private String title;
    private String content;
    private LocalDateTime dateCreated;
    private Tags tagName;
    private String email;
}
