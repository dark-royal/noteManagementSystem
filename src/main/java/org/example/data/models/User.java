package org.example.data.models;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;


import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@ToString
public class User {
    private String username;
    private String email;
    private String password;
    @Id
    private String id;
    private boolean loginStatus;
    private LocalDateTime sharedDate;
    private String noteTitle;
    private String noteContent;
    private List<Notes> notesList;
    public List<Notes> sharedNotesList;
    private List<Notes> receiverReceivedNote;


}
