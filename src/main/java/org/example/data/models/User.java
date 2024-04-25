package org.example.data.models;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class User {
    private String username;
    private String email;
    private String password;
    private String id;
    private boolean loginStatus;
    private LocalDateTime sharedDate;
    private String noteTitle;
    private String noteContent;

    @DBRef
    private List<Notes> notesList;
    @DBRef
    public List<Notes> sharedNotesList;
    @DBRef
    private List<Notes> receiverReceivedNote;



}
