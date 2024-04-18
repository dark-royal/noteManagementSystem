package org.example.services;

import org.example.data.models.Notes;
import org.example.data.models.Tags;
import org.example.data.models.User;
import org.example.dtos.request.*;
import org.example.dtos.responses.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    List<User> findAllUser();

    void lockNote(String password);



    RegisterUserResponse register(RegisterUserRequest registerUserRequest);

    void deleteAll();

    LoginUserResponse login(LoginUserRequest loginUserRequest);

    User findUserById(String id);

    LogoutUserResponse logout(LogoutUserRequest logoutUserRequest);

    List<User> getAllNote();

    List<Tags> getAllTags();

    CreateNoteResponse createNote(CreateNoteRequest createNoteRequest);

    DeleteNoteResponse deleteNote(DeleteNoteRequest deleteNoteRequest);

    List<Notes> findNoteByTagName(FindNoteRequest findNoteRequest);



    List<Notes> findAllNotesByEmail(String email);

    FindNoteResponse findNote(FindNoteRequest findNoteRequest);

    UpdateNoteResponse updateNote(UpdateNotesRequest updateNotesRequest);


}
