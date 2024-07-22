package org.example.dtos.responses;

import lombok.Getter;
import lombok.Setter;
import org.example.data.models.Notes;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ShareNoteResponse {
    private List<Notes> noteList = new ArrayList<>();
    private String message;
    private boolean successStatus;
    private String noteTitle;
    private String noteContent;
    private String senderEmail;
    private String receiverEmail;
    private LocalDateTime dateShared;
    @Id
    private String id;

    public ShareNoteResponse(){
        dateShared = LocalDateTime.now();
    }



}
