package org.example.controllers;

import org.example.dtos.request.LoginUserRequest;
import org.example.dtos.request.LogoutUserRequest;
import org.example.dtos.request.RegisterUserRequest;
import org.example.dtos.responses.LoginUserResponse;
import org.example.dtos.responses.LogoutUserResponse;
import org.example.dtos.responses.RegisterUserResponse;
import org.example.exceptions.UserExistException;
import org.example.exceptions.UserNotFoundException;
import org.example.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

@PostMapping("/signup")
    public ResponseEntity<RegisterUserResponse> register(@RequestBody RegisterUserRequest registerUserRequest){
        try {
            RegisterUserResponse response = userService.register(registerUserRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            throw new UserExistException("%s exist already",registerUserRequest.getEmail());

        }


    }

    @PostMapping("/login")
    public ResponseEntity<LoginUserResponse> login(@RequestBody LoginUserRequest loginUserRequest){
        try {
            LoginUserResponse loginUserResponse = userService.login(loginUserRequest);
            return ResponseEntity.status(HttpStatus.OK).body(loginUserResponse);
        } catch (Exception e) {
            throw new UserNotFoundException("user not found");
        }




    }
    @PostMapping("/logout")
    public ResponseEntity<LogoutUserResponse> logout(@RequestBody LogoutUserRequest logoutUserRequest){
        try {
            LogoutUserResponse logoutUserResponse = userService.logout(logoutUserRequest);
            return ResponseEntity.status(HttpStatus.OK).body(logoutUserResponse);
        } catch (Exception e) {
            throw new UserNotFoundException("user not found");
        }
    }

}
