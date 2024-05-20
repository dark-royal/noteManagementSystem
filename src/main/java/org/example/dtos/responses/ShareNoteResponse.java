package org.example.dtos.responses;

import lombok.Data;
import org.example.data.models.Notes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class ShareNoteResponse {
    private List<Notes> noteList = new ArrayList<>();
    private String message;
    private boolean successStatus;
    private String noteTitle;
    private String noteContent;
    private String senderEmail;
    private String receiverEmail;
    private LocalDateTime dateShared;
    private String id;

    public ShareNoteResponse(){
        dateShared = LocalDateTime.now();
    }



}
