package org.example.data.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class User {
    private String username;
    private String email;
//    private Notes notesTitle;
//    private Notes noteContent;
    private String password;
    private String id;
    private boolean loginStatus;
    private LocalDateTime sharedDate;
    private String noteTitle;
    private String noteContent;

    @DBRef
    private List<Notes> notesList = new ArrayList<>();
    @DBRef
    public List<Notes> sharedNotesList  = new ArrayList<>();;
    @DBRef
    private List<Notes> receiverReceivedNote  = new ArrayList<>();;


}
