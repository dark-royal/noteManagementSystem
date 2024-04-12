package org.example.services;

import org.example.data.models.User;
import org.example.data.repositories.UserRepository;
import org.example.dtos.request.LoginUserRequest;
import org.example.dtos.request.LogoutUserRequest;
import org.example.dtos.request.RegisterUserRequest;
import org.example.dtos.responses.LoginUserResponse;
import org.example.dtos.responses.LogoutUserResponse;
import org.example.dtos.responses.RegisterUserResponse;
import org.example.exceptions.InvalidPasswordException;
import org.example.exceptions.UserExistException;
import org.example.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Override
    public List<User> findAllUser() {
        return userRepository.findAll();
    }

    @Override
    public RegisterUserResponse register(RegisterUserRequest registerUserRequest) {
        validateUser(registerUserRequest.getEmail());
        User user = new User();
        user.setUsername(registerUserRequest.getUsername());
        user.setPassword(registerUserRequest.getPassword());
        user.setEmail(registerUserRequest.getEmail());
        var savedUser = userRepository.save(user);

        RegisterUserResponse response = new RegisterUserResponse();
        response.setMessage("registered successfully");
        response.setUserId(savedUser.getId());
        return response;
    }

    @Override
    public void deleteAll() {
        userRepository.deleteAll();
    }

    @Override
    public LoginUserResponse login(LoginUserRequest loginUserRequest) {
        User user = userRepository.findUserByEmail(loginUserRequest.getEmail()).orElseThrow(()-> new UserNotFoundException("User not found"));
            if(user.getPassword().equalsIgnoreCase(loginUserRequest.getPassword())) {
                user.setLoginStatus(true);
                userRepository.save(user);
                LoginUserResponse loginUserResponse = new LoginUserResponse();
                loginUserResponse.setMessage("login successfully");
                return loginUserResponse;
            }
        else{
            throw new InvalidPasswordException("invalid password");
        }



        }



    @Override
    public User findUserById(String id) {
        return userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("user not found"));
    }

    @Override
    public LogoutUserResponse logout(LogoutUserRequest logoutUserRequest) {
        User user = userRepository.findUserByEmail(logoutUserRequest.getEmail()).orElseThrow(()-> new UserNotFoundException("User not found"));
            user.setLoginStatus(false);
            userRepository.save(user);
            LogoutUserResponse logoutUserResponse = new LogoutUserResponse();
            logoutUserResponse.setMessage("login successfully");
            return logoutUserResponse;
    }


    public void validateUser(String email){
        Optional<User> user = userRepository.findUserByEmail(email);
        if(user.isPresent()) throw new UserExistException("%s exist already",email);

    }

    public void validateLogin(){

    }

}
