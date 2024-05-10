package org.example.dtos.request;

import lombok.Data;
import org.example.data.models.Notes;

import java.util.List;

@Data

public class RegisterUserRequest {
    private String username;
    private String password;
    private List<Notes> noteList;
    private String email;


}

