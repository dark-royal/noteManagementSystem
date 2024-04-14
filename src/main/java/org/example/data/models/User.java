package org.example.data.models;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;

@Data
public class User {
    private String username;
    private String email;
    private String password;
    private String id;
    private boolean loginStatus;
    @DBRef
    private List<Notes> notesList;



}
