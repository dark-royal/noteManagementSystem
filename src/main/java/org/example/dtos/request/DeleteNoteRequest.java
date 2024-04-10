package org.example.dtos.request;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class DeleteNoteRequest {
    @Id
    public String id;
    private String title;
}
