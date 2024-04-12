package org.example.dtos.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterUserRequest {
    private String username;
    private String password;
    private String email;


}
