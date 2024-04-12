package org.example.services;

import org.example.data.models.User;
import org.example.dtos.request.LoginUserRequest;
import org.example.dtos.request.LogoutUserRequest;
import org.example.dtos.request.RegisterUserRequest;
import org.example.dtos.responses.LoginUserResponse;
import org.example.dtos.responses.LogoutUserResponse;
import org.example.dtos.responses.RegisterUserResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    List<User> findAllUser();

    RegisterUserResponse register(RegisterUserRequest registerUserRequest);

    void deleteAll();

    LoginUserResponse login(LoginUserRequest loginUserRequest);

    User findUserById(String id);

    LogoutUserResponse logout(LogoutUserRequest logoutUserRequest);

}
