package org.example.controllers;

import org.example.data.models.Notes;
import org.example.dtos.request.*;
import org.example.dtos.responses.*;
import org.example.exceptions.NoteAlreadyExistException;
import org.example.exceptions.NoteNotFoundException;
import org.example.exceptions.UserExistException;
import org.example.exceptions.UserNotFoundException;
import org.example.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/createNote")
    public ResponseEntity<CreateNoteResponse> createNote(CreateNoteRequest createNoteRequest){
        try {
            CreateNoteResponse createNoteResponse = userService.createNote(createNoteRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(createNoteResponse);
        } catch (Exception e) {
            throw new NoteAlreadyExistException("note exist already");
        }
    }

    @PostMapping("/deleteNote")
    public ResponseEntity<DeleteNoteResponse> deleteNote(DeleteNoteRequest deleteNoteRequest){
        try {
            DeleteNoteResponse response = userService.deleteNote(deleteNoteRequest);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            throw new NoteNotFoundException("Note not found");
        }
    }

    @GetMapping("/findNote")
    public List<Notes> findNote(FindNoteRequest findNoteRequest){
        return userService.findNoteByTagName(findNoteRequest);

    }

    @PostMapping
    public ResponseEntity<UpdateNoteResponse> updateNote(UpdateNotesRequest updateNotesRequest){
        try {
            UpdateNoteResponse updateNoteResponse = userService.updateNote(updateNotesRequest);
            return ResponseEntity.status(HttpStatus.OK).body(updateNoteResponse);
        } catch (Exception e) {
            throw new NoteNotFoundException("Note not found");
        }

    }



}
