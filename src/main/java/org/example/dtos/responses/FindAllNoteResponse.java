package org.example.dtos.responses;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Getter
@Setter
public class FindAllNoteResponse {
    @Id
    private String id;
    private String email;
    private String title;
    private LocalDateTime dateCreated;
    private String content;
    private boolean lockStatus;

}
