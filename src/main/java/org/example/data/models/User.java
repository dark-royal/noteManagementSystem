package org.example.data.models;

import lombok.Data;

import java.util.List;

@Data
public class User {
    private String username;
    private String email;
    private String password;
    private String id;
    private boolean loginStatus;
    private List<Notes> notesList;



}
