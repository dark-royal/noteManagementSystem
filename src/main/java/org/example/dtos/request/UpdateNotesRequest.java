package org.example.dtos.request;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
@Data
public class UpdateNotesRequest {
    @Id
    private String id;
    private String title;
    private String content;
    private LocalDateTime newDateCreated;
}
