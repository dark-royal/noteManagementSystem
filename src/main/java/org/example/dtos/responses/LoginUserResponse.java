package org.example.dtos.responses;

import lombok.Data;

@Data
public class LoginUserResponse {
    private String message;
    private boolean loginStatus;
}
