package org.example.dtos.responses;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginUserResponse {
    private String message;
    private boolean loginStatus;
}
