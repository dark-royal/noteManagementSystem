package org.example.dtos.request;

import lombok.Data;

@Data
public class LoginUserRequest {
    private boolean logStatus;
    private String email;
    private String password;
}
