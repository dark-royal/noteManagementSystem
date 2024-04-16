package org.example.dtos.responses;

import lombok.Data;
import org.example.data.models.User;

@Data
public class RegisterUserResponse {
    private String message;
    private String email;
    private String username;
    private String userId;
}
