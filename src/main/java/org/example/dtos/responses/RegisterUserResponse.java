package org.example.dtos.responses;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.example.data.models.User;

@Getter
@Setter
public class RegisterUserResponse {
    private String message;
    private String email;
    private String username;
    private String userId;
}
