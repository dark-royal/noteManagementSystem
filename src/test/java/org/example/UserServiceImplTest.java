package org.example;

import org.example.data.models.Notes;
import org.example.data.models.Tags;
import org.example.dtos.request.*;
import org.example.dtos.responses.*;
import org.example.exceptions.InvalidPasswordException;
import org.example.exceptions.UserExistException;
import org.example.exceptions.UserNotLoggedInException;
import org.example.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceImplTest {
    @Autowired
    private UserService userService;


    @BeforeEach
    public void setUserService(){
        userService.deleteAll();
    }

    @Test
    public void testUserCanRegister(){
        RegisterUserRequest registerUserRequest = registerRequest("praise@gmail.com","israel","darkRoyal");
        RegisterUserResponse registerUserResponse = userService.register(registerUserRequest);
        assertThat(registerUserResponse.getMessage()).isNotNull();
        assertEquals(1,userService.findAllUser().size());


    }

    @Test
    public void testThatUserCannotBeRegisterWithTheSameDetails_throwUserExistException(){
        RegisterUserRequest registerUserRequest = registerRequest("praise1@gmail.com","israel","darkRoyal");
        RegisterUserResponse registerUserResponse = userService.register(registerUserRequest);
        assertThat(registerUserResponse.getMessage()).isNotNull();
        assertEquals(1,userService.findAllUser().size());

        assertThrows(UserExistException.class,()->userService.register(registerUserRequest));
    }

    @Test
    public void testThatUserCanRegister_login(){
        RegisterUserRequest registerUserRequest = registerRequest("praise2@gmail.com","israel","darkRoyal");
        RegisterUserResponse registerUserResponse = userService.register(registerUserRequest);
        assertThat(registerUserResponse.getMessage()).isNotNull();
        assertEquals(1,userService.findAllUser().size());

        LoginUserRequest loginUserRequest = new LoginUserRequest();
        loginUserRequest.setEmail(registerUserRequest.getEmail());
        loginUserRequest.setPassword(registerUserRequest.getPassword());
        LoginUserResponse response = userService.login(loginUserRequest);
        assertThat(response.getMessage()).isNotNull();
        assertThat(registerUserResponse.getUserId()).isNotNull();
        assertTrue(userService.findUserById(registerUserResponse.getUserId()).isLoginStatus());

    }

    @Test
    public void register_login_logout(){
        RegisterUserRequest registerUserRequest = registerRequest("praise3@gmail.com","israel1","darkRoyal1");
        RegisterUserResponse registerUserResponse = userService.register(registerUserRequest);
        assertThat(registerUserResponse.getMessage()).isNotNull();
        assertEquals(1,userService.findAllUser().size());

        LoginUserRequest loginUserRequest = new LoginUserRequest();
        loginUserRequest.setEmail(registerUserRequest.getEmail());
        loginUserRequest.setPassword(registerUserRequest.getPassword());
        LoginUserResponse response = userService.login(loginUserRequest);
        assertThat(response.getMessage()).isNotNull();
        assertThat(registerUserResponse.getUserId()).isNotNull();
        assertTrue(userService.findUserById(registerUserResponse.getUserId()).isLoginStatus());

        LogoutUserRequest logoutUserRequest = new LogoutUserRequest();
        logoutUserRequest.setEmail(registerUserRequest.getEmail());
        LogoutUserResponse response1 = userService.logout(logoutUserRequest);
        assertThat(response1.getMessage()).isNotNull();
        assertFalse(userService.findUserById(registerUserResponse.getUserId()).isLoginStatus());

    }

    @Test
    public void testThatUserCannotLoginWithIncorrectDetails(){
        RegisterUserRequest registerUserRequest = registerRequest("praise3@gmail.com","israel1","darkRoyal1");
        RegisterUserResponse registerUserResponse = userService.register(registerUserRequest);
        assertThat(registerUserResponse.getMessage()).isNotNull();
        assertEquals(1,userService.findAllUser().size());

        LoginUserRequest loginUserRequest = new LoginUserRequest();
        loginUserRequest.setEmail(registerUserRequest.getEmail());
        loginUserRequest.setPassword("praise");
        assertThrows(InvalidPasswordException.class,()->userService.login(loginUserRequest));


    }

    @Test
    public void testUserCannotLogout_without_loggingIn(){
        RegisterUserRequest registerUserRequest = registerRequest("praise3@gmail.com","israel1","darkRoyal1");
        RegisterUserResponse registerUserResponse = userService.register(registerUserRequest);
        assertThat(registerUserResponse.getMessage()).isNotNull();
        assertEquals(1,userService.findAllUser().size());

        LogoutUserRequest logoutUserRequest = new LogoutUserRequest();
        logoutUserRequest.setEmail(registerUserRequest.getEmail());
        assertThrows(UserNotLoggedInException.class,()->userService.logout(logoutUserRequest));

    }


    @Test
    public void testThatUserCanCreateNote(){
        RegisterUserRequest registerUserRequest = registerRequest("praise3@gmail.com","israel1","darkRoyal1");
        RegisterUserResponse registerUserResponse = userService.register(registerUserRequest);
        assertThat(registerUserResponse.getMessage()).isNotNull();
        assertEquals(1,userService.findAllUser().size());

        LoginUserRequest loginUserRequest = new LoginUserRequest();
        loginUserRequest.setEmail(registerUserRequest.getEmail());
        loginUserRequest.setPassword(registerUserRequest.getPassword());
        LoginUserResponse response = userService.login(loginUserRequest);
        assertThat(response.getMessage()).isNotNull();
        assertTrue(userService.findUserById(registerUserResponse.getUserId()).isLoginStatus());

        CreateNoteRequest createNoteRequest = new CreateNoteRequest();
        createNoteRequest.setTitle("Precious");
        createNoteRequest.setContent("precious is a good girl i guess");
        createNoteRequest.setDateCreated(LocalDateTime.now());

        Tags tags = new Tags();
        tags.setName("work");
        createNoteRequest.setTagName(tags);


        CreateNoteResponse createNoteResponse = userService.createNote(createNoteRequest);
        assertThat(createNoteResponse.getMessage()).isNotNull();
        assertEquals(1,userService.getAllNote().size());



    }

    @Test
    public void testThatNoteCanBeDeleted(){
        RegisterUserRequest registerUserRequest = registerRequest("praise3@gmail.com","israel1","darkRoyal1");
        RegisterUserResponse registerUserResponse = userService.register(registerUserRequest);
        assertThat(registerUserResponse.getMessage()).isNotNull();
        assertEquals(1,userService.findAllUser().size());

        LoginUserRequest loginUserRequest = new LoginUserRequest();
        loginUserRequest.setEmail(registerUserRequest.getEmail());
        loginUserRequest.setPassword(registerUserRequest.getPassword());
        LoginUserResponse response = userService.login(loginUserRequest);
        assertThat(response.getMessage()).isNotNull();
        assertTrue(userService.findUserById(registerUserResponse.getUserId()).isLoginStatus());

        CreateNoteRequest createNoteRequest = new CreateNoteRequest();
        createNoteRequest.setTitle("Precious1");
        createNoteRequest.setContent("precious is a good girl i guess1");
        createNoteRequest.setDateCreated(LocalDateTime.now());

        Tags tags = new Tags();
        tags.setName("work");
        createNoteRequest.setTagName(tags);

        CreateNoteResponse response1 = userService.createNote(createNoteRequest);
        assertThat(response1.getMessage()).isNotNull();
        assertEquals(1,userService.getAllNote().size());


        DeleteNoteRequest deleteNoteRequest = new DeleteNoteRequest();
        deleteNoteRequest.setTitle("praise");
        DeleteNoteResponse response2 = userService.deleteNote(deleteNoteRequest);
        assertThat(response2.getMessage()).isNotNull();
        assertEquals(1,userService.getAllNote().size());


    }

//    @Test
//    public void testNoteCanBe


    public RegisterUserRequest registerRequest(String email, String password, String username){
        return RegisterUserRequest.builder()
                .email(email)
                .password(password)
                .username(username)
                .build();
    }



}
