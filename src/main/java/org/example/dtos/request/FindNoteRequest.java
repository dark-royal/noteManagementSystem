package org.example.dtos.request;

import lombok.Data;

@Data
public class FindNoteRequest {
    private String title;
    private String email;
    private String content;
    private String tagName;
}
