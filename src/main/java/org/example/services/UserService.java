package org.example.services;

import org.example.data.models.User;
import org.example.dtos.request.*;
import org.example.dtos.responses.*;
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

    List<User> getAllNote();

    CreateNoteResponse createNote(CreateNoteRequest createNoteRequest);

    DeleteNoteResponse deleteNote(DeleteNoteRequest deleteNoteRequest);

}
