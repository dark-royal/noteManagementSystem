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


    LockNoteResponse lockNote(LockNoteRequest lockNoteRequest);

    RegisterUserResponse register(RegisterUserRequest registerUserRequest);

    void deleteAll();

    LoginUserResponse login(LoginUserRequest loginUserRequest);

    User findUserById(String id);

    LogoutUserResponse logout(LogoutUserRequest logoutUserRequest);


    List<Notes> getAllNotesByUser(String email);

    List<Tags> getAllTags();

    CreateNoteResponse createNote(CreateNoteRequest createNoteRequest);

    DeleteNoteResponse deleteNote(DeleteNoteRequest deleteNoteRequest);

    List<Notes> findNoteByTagName(FindNoteRequest findNoteRequest);



    List<FindAllNoteResponse> findAllNotesByEmail(FindAllNoteRequest findAllNoteRequest);

//    Notes findNote(FindNoteRequest findNoteRequest);

    UpdateNoteResponse updateNote(UpdateNotesRequest updateNotesRequest);

    List<Notes> shareNote(ShareNoteRequest shareNoteRequest);

    MakeSFavoriteResponse makeFavorites(MakeFavoritesRequest makeFavoritesRequest);

    UnlockNoteResponse unlockNote(UnlockNoteRequest unlockNoteRequest);
}
