package org.example.dtos.request;

import lombok.Data;

@Data
public class ShareNoteRequest {
    private String senderEmail;
    private String id;
    private String receiverEmail;
    private String noteTitle;
    private String noteContent;
}
