package org.example.controllers;

import org.example.data.models.Notes;
import org.example.dtos.request.*;
import org.example.dtos.responses.*;
import org.example.exceptions.*;
import org.example.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/User")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody RegisterUserRequest registerUserRequest){
        try {
            RegisterUserResponse response = userService.register(registerUserRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (UserExistException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginUserRequest loginUserRequest){
        try {
            LoginUserResponse loginUserResponse = userService.login(loginUserRequest);
            return ResponseEntity.status(HttpStatus.OK).body(loginUserResponse);
        } catch (InvalidPasswordException | UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }




    }
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody LogoutUserRequest logoutUserRequest){
        try {
            LogoutUserResponse logoutUserResponse = userService.logout(logoutUserRequest);
            return ResponseEntity.status(HttpStatus.OK).body(logoutUserResponse);
        } catch (UserNotFoundException | UserNotLoggedInException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/createNote")
    public ResponseEntity<?> createNote(@RequestBody CreateNoteRequest createNoteRequest){
        System.out.println("this is the CreateNoteRequest"+ createNoteRequest);
        System.out.println(createNoteRequest);

        try {
            CreateNoteResponse createNoteResponse = userService.createNote(createNoteRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(createNoteResponse);
        } catch (NoteAlreadyExistException| UserNotLoggedInException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/deleteNote")
    public ResponseEntity<?> deleteNote(DeleteNoteRequest deleteNoteRequest){
        try {
            DeleteNoteResponse response = userService.deleteNote(deleteNoteRequest);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (UserNotFoundException | NoteNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/findNote")
    public List<Notes> findNote(FindNoteRequest findNoteRequest){
        return userService.findNoteByTagName(findNoteRequest);

    }

    @PostMapping("/updateNote")
    public ResponseEntity<?> updateNote(UpdateNotesRequest updateNotesRequest){
        try {
            UpdateNoteResponse updateNoteResponse = userService.updateNote(updateNotesRequest);
            return ResponseEntity.status(HttpStatus.OK).body(updateNoteResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @GetMapping("/findAllNoteByEmail{email}")
    public ResponseEntity<List<?>> findAllNoteOfUser(FindAllNoteRequest findAllNoteRequest){
        try{
            List<FindAllNoteResponse> findAllNoteResponse = userService.findAllNotesByEmail(findAllNoteRequest);
            return ResponseEntity.status(HttpStatus.OK).body(findAllNoteResponse);
        }catch (UserNoteListIsEmptyException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonList(e.getMessage()));

        }
    }



}
