package org.example.dtos.request;


import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class ShareNoteRequest {
    private String senderEmail;
    @Id
    private String id;
    private String receiverEmail;
    private String noteTitle;
    private String noteContent;
}
