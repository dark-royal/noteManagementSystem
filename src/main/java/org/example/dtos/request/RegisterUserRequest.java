package org.example.dtos.request;


import lombok.Data;
import org.example.data.models.Notes;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data

public class RegisterUserRequest {
    @Id
    private String id;
    private String username;
    private String password;
    private List<Notes> noteList;
    private String email;


}

