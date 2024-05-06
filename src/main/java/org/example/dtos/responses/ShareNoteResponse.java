package org.example.dtos.responses;

import lombok.Data;
import org.example.data.models.Notes;

import java.time.LocalDateTime;

@Data
public class ShareNoteResponse {
    private String message;
    private boolean successStatus;
    private String noteTitle;
    private String noteContent;
    private String senderEmail;
    private String receiverEmail;
    private LocalDateTime dateShared;



    public void setNoteShared() {
    }

    public void setNoteTitle() {
    }

    public void setDateShared() {
    }

    public void setSenderEmail() {
    }

    public void setReceiverEmail() {
    }
}
