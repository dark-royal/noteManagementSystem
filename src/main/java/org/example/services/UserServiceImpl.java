package org.example.services;

import org.example.data.models.Notes;
import org.example.data.models.Tags;
import org.example.data.models.User;
import org.example.data.repositories.NoteRepository;
import org.example.data.repositories.TagRepository;
import org.example.data.repositories.UserRepository;
import org.example.dtos.request.CreateNoteRequest;
import org.example.dtos.request.LoginUserRequest;
import org.example.dtos.request.LogoutUserRequest;
import org.example.dtos.request.RegisterUserRequest;
import org.example.dtos.responses.CreateNoteResponse;
import org.example.dtos.responses.LoginUserResponse;
import org.example.dtos.responses.LogoutUserResponse;
import org.example.dtos.responses.RegisterUserResponse;
import org.example.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    private List<Notes>noteList = new ArrayList<>();
    @Autowired
    private NoteRepository noteRepository;
    @Autowired
    private TagRepository tagRepository;
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
        validateLogin(logoutUserRequest.getEmail());
        User user = userRepository.findUserByEmail(logoutUserRequest.getEmail()).orElseThrow(()-> new UserNotFoundException("User not found"));
            user.setLoginStatus(false);
            userRepository.save(user);
            LogoutUserResponse logoutUserResponse = new LogoutUserResponse();
            logoutUserResponse.setMessage("login successfully");
            return logoutUserResponse;
    }

    @Override
    public List<User> getAllNote() {
        return userRepository.findAll();
    }

    @Override
    public CreateNoteResponse createNote(CreateNoteRequest createNoteRequest) {
        validateLogin(createNoteRequest.getEmail());
        validateCreateNote(createNoteRequest.getContent(), createNoteRequest.getTitle());
        Notes note = new Notes();
        note.setTitle(createNoteRequest.getTitle());
        note.setContent(createNoteRequest.getContent());
        note.setDateAndTimeCreated(createNoteRequest.getDateCreated());
        var savedNote = noteRepository.save(note);
        Tags tags = new Tags();
            tags.setId(savedNote.getId());
            tags.setName(tags.getName());
            tagRepository.save(tags);
            noteList.add(savedNote);

        CreateNoteResponse createNoteResponse = new CreateNoteResponse();
        createNoteResponse.setMessage("create note successfully");
        createNoteResponse.setId(savedNote.getId());
        return createNoteResponse;
    }

    private void validateCreateNote(String content, String title) {
        Notes note = noteRepository.findByContentAndTitle(content, title);
        if (note != null) throw new NoteAlreadyExistException("note exist already");


    }


    public void validateUser(String email){
        Optional<User> user = userRepository.findUserByEmail(email);
        if(user.isPresent()) throw new UserExistException("%s exist already",email);

    }

    public void validateLogin(String email){
       User user = userRepository.findUserByEmail(email).orElseThrow(()->new UserNotFoundException("user not found"));
        if(!user.isLoginStatus())throw new UserNotLoggedInException("Pls log in ");

    }

}
