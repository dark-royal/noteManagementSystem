package org.example;

import org.example.data.models.Notes;
import org.example.data.models.Tags;
import org.example.data.repositories.NoteRepository;
import org.example.dtos.request.*;
import org.example.dtos.responses.*;
import org.example.exceptions.*;
import org.example.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceImplTest {
    @Autowired
    private UserService userService;
    @Autowired
    private NoteRepository noteRepository;


    @BeforeEach
    public void setUserService() {
        noteRepository.deleteAll();
        userService.deleteAll();
    }

    @Test
    public void testUserCanRegister() {
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setEmail("praise@gmail.com");
        registerUserRequest.setUsername("praisey");
        registerUserRequest.setPassword("myLife1@");
        RegisterUserResponse registerUserResponse = userService.register(registerUserRequest);
        assertThat(registerUserResponse.getMessage()).isNotNull();
        assertEquals(1, userService.findAllUser().size());


    }

    @Test
    public void testThatUserCannotBeRegisterWithTheSameDetails_throwUserExistException() {
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setEmail("praise1@gmail.com");
        registerUserRequest.setUsername("praiseyo");
        registerUserRequest.setPassword("myLife123@");
        RegisterUserResponse registerUserResponse = userService.register(registerUserRequest);
        assertThat(registerUserResponse.getMessage()).isNotNull();
        assertEquals(1, userService.findAllUser().size());

        assertThrows(UserExistException.class, () -> userService.register(registerUserRequest));
    }

    @Test
    public void testThatUserCanRegister_login() {
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setEmail("praise2@gmail.com");
        registerUserRequest.setUsername("praisey1");
        registerUserRequest.setPassword("myLife1@");
        RegisterUserResponse registerUserResponse = userService.register(registerUserRequest);
        assertThat(registerUserResponse.getMessage()).isNotNull();
        assertEquals(1, userService.findAllUser().size());
        LoginUserRequest loginUserRequest = new LoginUserRequest();
        loginUserRequest.setEmail(registerUserRequest.getEmail());
        loginUserRequest.setPassword(registerUserRequest.getPassword());
        LoginUserResponse response = userService.login(loginUserRequest);
        assertThat(response.getMessage()).isNotNull();
        assertThat(registerUserResponse.getUserId()).isNotNull();
        assertTrue(userService.findUserById(registerUserResponse.getUserId()).isLoginStatus());

    }

    @Test
    public void register_login_logout() {
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setEmail("praise3@gmail.com");
        registerUserRequest.setUsername("praisey2");
        registerUserRequest.setPassword("myLife1@");
        RegisterUserResponse registerUserResponse = userService.register(registerUserRequest);
        assertThat(registerUserResponse.getMessage()).isNotNull();
        assertEquals(1, userService.findAllUser().size());

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
    public void testThatUserCannotLoginWithIncorrectDetails() {
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setEmail("praise4@gmail.com");
        registerUserRequest.setUsername("praisey3");
        registerUserRequest.setPassword("myLife1@");
        RegisterUserResponse registerUserResponse = userService.register(registerUserRequest);
        assertThat(registerUserResponse.getMessage()).isNotNull();
        assertEquals(1, userService.findAllUser().size());

        LoginUserRequest loginUserRequest = new LoginUserRequest();
        loginUserRequest.setEmail(registerUserRequest.getEmail());
        loginUserRequest.setPassword("praise");
        assertThrows(InvalidPasswordException.class, () -> userService.login(loginUserRequest));


    }

    @Test
    public void testUserCannotLogout_without_loggingIn() {
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setEmail("praise5@gmail.com");
        registerUserRequest.setUsername("praisey5");
        registerUserRequest.setPassword("myLife1@");
        RegisterUserResponse registerUserResponse = userService.register(registerUserRequest);
        assertThat(registerUserResponse.getMessage()).isNotNull();
        assertEquals(1, userService.findAllUser().size());

        LogoutUserRequest logoutUserRequest = new LogoutUserRequest();
        logoutUserRequest.setEmail(registerUserRequest.getEmail());
        assertThrows(UserNotLoggedInException.class, () -> userService.logout(logoutUserRequest));

    }


    @Test
    public void testThatUserCanCreateNote() {
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setEmail("praise7@gmail.com");
        registerUserRequest.setUsername("praisey7");
        registerUserRequest.setPassword("myLife1@");
        RegisterUserResponse registerUserResponse = userService.register(registerUserRequest);
        assertThat(registerUserResponse.getMessage()).isNotNull();
        assertEquals(1, userService.findAllUser().size());

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
        createNoteRequest.setEmail("praise7@gmail.com");
        Tags tags = new Tags();
        tags.setName("work");
        createNoteRequest.setTagName(tags);


        CreateNoteResponse createNoteResponse = userService.createNote(createNoteRequest);
        assertThat(createNoteResponse.getMessage()).isNotNull();
        assertEquals(1, userService.getAllNote(registerUserResponse.getEmail()).size());


    }


    @Test
    public void testThatNoteCanBeDeleted() {
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setEmail("praise8@gmail.com");
        registerUserRequest.setUsername("praise6");
        registerUserRequest.setPassword("myLife1@");
        RegisterUserResponse registerUserResponse = userService.register(registerUserRequest);
        assertThat(registerUserResponse.getMessage()).isNotNull();
        assertEquals(1, userService.findAllUser().size());

        LoginUserRequest loginUserRequest = new LoginUserRequest();
        loginUserRequest.setEmail(registerUserRequest.getEmail());
        loginUserRequest.setPassword(registerUserRequest.getPassword());
        LoginUserResponse response = userService.login(loginUserRequest);
        assertThat(response.getMessage()).isNotNull();
        assertTrue(userService.findUserById(registerUserResponse.getUserId()).isLoginStatus());

        CreateNoteRequest createNoteRequest = new CreateNoteRequest();
        createNoteRequest.setTitle("heritage");
        createNoteRequest.setContent("heritage is a good girl i guess1");
        createNoteRequest.setDateCreated(LocalDateTime.now());
        createNoteRequest.setEmail(registerUserRequest.getEmail());

        Tags tags = new Tags();
        tags.setName("personal");
        createNoteRequest.setTagName(tags);

        CreateNoteResponse response1 = userService.createNote(createNoteRequest);
        assertThat(response1.getMessage()).isNotNull();
        assertEquals(1, userService.getAllNote(registerUserResponse.getEmail()).size());


        DeleteNoteRequest deleteNoteRequest = new DeleteNoteRequest();
        deleteNoteRequest.setTitle(createNoteRequest.getTitle());
        deleteNoteRequest.setEmail(registerUserRequest.getEmail());
        deleteNoteRequest.setId(response1.getId());
        DeleteNoteResponse response2 = userService.deleteNote(deleteNoteRequest);
        assertThat(response2.getMessage()).isNotNull();
        assertEquals(0, userService.getAllNote(registerUserResponse.getEmail()).size());


    }


    @Test
    public void testNoteCanBeFound() {
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setEmail("praise8@gmail.com");
        registerUserRequest.setUsername("praisey12");
        registerUserRequest.setPassword("myLife1@");
        RegisterUserResponse registerUserResponse = userService.register(registerUserRequest);
        assertThat(registerUserResponse.getMessage()).isNotNull();
        assertEquals(1, userService.findAllUser().size());

        LoginUserRequest loginUserRequest = new LoginUserRequest();
        loginUserRequest.setEmail(registerUserRequest.getEmail());
        loginUserRequest.setPassword(registerUserRequest.getPassword());
        LoginUserResponse response = userService.login(loginUserRequest);
        assertThat(response.getMessage()).isNotNull();
        assertTrue(userService.findUserById(registerUserResponse.getUserId()).isLoginStatus());

        CreateNoteRequest createNoteRequest = new CreateNoteRequest();
        createNoteRequest.setTitle("heritage");
        createNoteRequest.setContent("gideon is a good boy i guess1");
        createNoteRequest.setEmail(registerUserRequest.getEmail());
        createNoteRequest.setDateCreated(LocalDateTime.now());

        Tags tags = new Tags();
        tags.setName("personally");
        createNoteRequest.setTagName(tags);

        CreateNoteResponse response1 = userService.createNote(createNoteRequest);
        assertThat(response1.getMessage()).isNotNull();
        assertEquals(1, userService.getAllNote(registerUserRequest.getEmail()).size());


        FindNoteRequest findNoteRequest = new FindNoteRequest();
        findNoteRequest.setTagName("personally");
        findNoteRequest.setEmail(registerUserRequest.getEmail());
        List<Notes> notes = userService.findNoteByTagName(findNoteRequest);
        System.out.println(notes);
        assertEquals("heritage", notes.getFirst().getTitle());

    }


    @Test
    public void testThatNoteCanBeUpdated() {

        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setEmail("praise13@gmail.com");
        registerUserRequest.setUsername("praisey14");
        registerUserRequest.setPassword("myLife1@");
        RegisterUserResponse registerUserResponse = userService.register(registerUserRequest);
        assertThat(registerUserResponse.getMessage()).isNotNull();
        assertEquals(1, userService.findAllUser().size());

        LoginUserRequest loginUserRequest = new LoginUserRequest();
        loginUserRequest.setEmail(registerUserRequest.getEmail());
        loginUserRequest.setPassword(registerUserRequest.getPassword());
        LoginUserResponse response = userService.login(loginUserRequest);
        assertThat(response.getMessage()).isNotNull();
        assertTrue(userService.findUserById(registerUserResponse.getUserId()).isLoginStatus());


        CreateNoteRequest createNoteRequest = new CreateNoteRequest();
        createNoteRequest.setTitle("My next week");
        createNoteRequest.setContent("I know tomorrow will be good and better");
        createNoteRequest.setEmail(registerUserRequest.getEmail());
        createNoteRequest.setDateCreated(LocalDateTime.now());

        Tags tags = new Tags();
        tags.setName("Nxt week plan");
        createNoteRequest.setTagName(tags);

        CreateNoteResponse response1 = userService.createNote(createNoteRequest);
        assertThat(response.getMessage()).isNotNull();
        assertEquals(1, userService.getAllNote(registerUserRequest.getEmail()).size());

        UpdateNotesRequest updateNotesRequest = new UpdateNotesRequest();
        System.out.println("the response id ... " + response1.getId());
        updateNotesRequest.setId(response1.getId());
        updateNotesRequest.setTitle("My tomorrow my life1");
        updateNotesRequest.setEmail(createNoteRequest.getEmail());
        updateNotesRequest.setContent("i love this life1");
        UpdateNoteResponse updateNoteResponse = userService.updateNote(updateNotesRequest);
        assertNotNull(updateNoteResponse);
        assertThat(updateNoteResponse.getMessage()).isNotNull();
        assertEquals(1, userService.getAllNote(registerUserRequest.getEmail()).size());

    }

    @Test
    public void testThatAllUserNoteCanBeFound() {
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setEmail("abbey@gmail.com");
        registerUserRequest.setUsername("abbey");
        registerUserRequest.setPassword("Abbey11@");
        RegisterUserResponse registerUserResponse = userService.register(registerUserRequest);
        assertThat(registerUserResponse.getMessage()).isNotNull();
        assertEquals(1, userService.findAllUser().size());

        LoginUserRequest loginUserRequest = new LoginUserRequest();
        loginUserRequest.setEmail(registerUserRequest.getEmail());
        loginUserRequest.setPassword(registerUserRequest.getPassword());
        LoginUserResponse response = userService.login(loginUserRequest);
        assertThat(response.getMessage()).isNotNull();
        assertTrue(userService.findUserById(registerUserResponse.getUserId()).isLoginStatus());


        CreateNoteRequest createNoteRequest = new CreateNoteRequest();
        createNoteRequest.setTitle("Lambdas and stream");
        createNoteRequest.setContent("It is in chapter seventeen");
        createNoteRequest.setEmail(registerUserRequest.getEmail());
        createNoteRequest.setDateCreated(LocalDateTime.now());

        Tags tags = new Tags();
        tags.setName("java");
        createNoteRequest.setTagName(tags);

        CreateNoteResponse response1 = userService.createNote(createNoteRequest);
        assertThat(response1.getMessage()).isNotNull();
        //assertEquals(1, userService.getAllNote(registerUserRequest.getEmail()).size());


        CreateNoteRequest createNoteRequest1 = new CreateNoteRequest();
        createNoteRequest1.setTitle("Lambdas and stream");
        createNoteRequest1.setContent("It is in chapter seventeen");
        createNoteRequest1.setEmail(registerUserRequest.getEmail());
        createNoteRequest1.setDateCreated(LocalDateTime.now());

        Tags tags1 = new Tags();
        tags1.setName("javas");
        createNoteRequest1.setTagName(tags1);

        CreateNoteResponse response2 = userService.createNote(createNoteRequest1);
        assertThat(response2.getMessage()).isNotNull();

        assertEquals(2, userService.getAllNote(registerUserRequest.getEmail()).size());

        FindAllNoteRequest findAllNoteRequest = new FindAllNoteRequest();
        findAllNoteRequest.setEmail(registerUserRequest.getEmail());
        List<FindAllNoteResponse> userNotes = userService.findAllNotesByEmail(findAllNoteRequest);

        assertThat(userNotes).isNotEmpty();

        //assertEquals(2, userNotes.size());
    }


    @Test
    public void testShareNote() {
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setEmail("janet@gmail.com");
        registerUserRequest.setUsername("janet");
        registerUserRequest.setPassword("Janet11@");
        RegisterUserResponse registerUserResponse = userService.register(registerUserRequest);
        assertThat(registerUserResponse.getMessage()).isNotNull();


        LoginUserRequest loginUserRequest = new LoginUserRequest();
        loginUserRequest.setEmail(registerUserRequest.getEmail());
        loginUserRequest.setPassword(registerUserRequest.getPassword());
        LoginUserResponse response = userService.login(loginUserRequest);
        assertThat(response.getMessage()).isNotNull();
        assertTrue(userService.findUserById(registerUserResponse.getUserId()).isLoginStatus());


        RegisterUserRequest registerUserRequest1 = new RegisterUserRequest();
        registerUserRequest1.setEmail("ola@gmail.com");
        registerUserRequest1.setUsername("ola");
        registerUserRequest1.setPassword("Ola1wer@3");
        RegisterUserResponse registerUserResponse1 = userService.register(registerUserRequest1);
        assertThat(registerUserResponse1.getMessage()).isNotNull();
        assertEquals(2, userService.findAllUser().size());

        LoginUserRequest loginUserRequest1 = new LoginUserRequest();
        loginUserRequest1.setEmail(registerUserRequest1.getEmail());
        loginUserRequest1.setPassword(registerUserRequest1.getPassword());
        LoginUserResponse response1 = userService.login(loginUserRequest1);
        assertThat(response1.getMessage()).isNotNull();
        assertTrue(userService.findUserById(registerUserResponse1.getUserId()).isLoginStatus());


        CreateNoteRequest createNoteRequest = new CreateNoteRequest();
        createNoteRequest.setTitle("array and stream");
        createNoteRequest.setContent("It is in chapter seven");
        createNoteRequest.setEmail(registerUserRequest.getEmail());
        createNoteRequest.setDateCreated(LocalDateTime.now());

        Tags tags = new Tags();
        tags.setName("java");
        createNoteRequest.setTagName(tags);

        CreateNoteResponse response2 = userService.createNote(createNoteRequest);
        assertThat(response2.getMessage()).isNotNull();
        assertEquals(1, userService.getAllNote(registerUserRequest.getEmail()).size());


        CreateNoteRequest createNoteRequest1 = new CreateNoteRequest();
        createNoteRequest1.setTitle("Lambdas and stream");
        createNoteRequest1.setContent("It is in chapter seventeen");
        createNoteRequest1.setEmail(registerUserRequest.getEmail());
        createNoteRequest1.setDateCreated(LocalDateTime.now());

        Tags tags1 = new Tags();
        tags1.setName("java");
        createNoteRequest1.setTagName(tags1);

        CreateNoteResponse response3 = userService.createNote(createNoteRequest1);
        assertThat(response3.getMessage()).isNotNull();

        assertEquals(2, userService.getAllNote(registerUserRequest.getEmail()).size());

        ShareNoteRequest shareNoteRequest = new ShareNoteRequest();
        shareNoteRequest.setReceiverEmail(registerUserRequest1.getEmail());
        shareNoteRequest.setSenderEmail(registerUserRequest.getEmail());
        shareNoteRequest.setId(createNoteRequest.getId());
        shareNoteRequest.setNoteTitle(createNoteRequest.getTitle());
        shareNoteRequest.setNoteContent(createNoteRequest.getContent());

        ShareNoteResponse shareNoteResponse = userService.shareNote(shareNoteRequest);
        assertThat(shareNoteResponse.getMessage()).isNotNull();
        assertEquals(2, userService.getAllNote(registerUserRequest.getEmail()).size());
        assertEquals(1, userService.getAllNote(registerUserRequest1.getEmail()).size());
    }

    @Test
    public void lockNote() {
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setEmail("orisha@gmail.com");
        registerUserRequest.setUsername("orisha");
        registerUserRequest.setPassword("Orisha1@");
        RegisterUserResponse registerUserResponse = userService.register(registerUserRequest);
        assertThat(registerUserResponse.getMessage()).isNotNull();
        assertEquals(1, userService.findAllUser().size());

        LoginUserRequest loginUserRequest = new LoginUserRequest();
        loginUserRequest.setEmail(registerUserRequest.getEmail());
        loginUserRequest.setPassword(registerUserRequest.getPassword());
        LoginUserResponse response = userService.login(loginUserRequest);
        assertThat(response.getMessage()).isNotNull();
        assertTrue(userService.findUserById(registerUserResponse.getUserId()).isLoginStatus());


        CreateNoteRequest createNoteRequest = new CreateNoteRequest();
        createNoteRequest.setTitle("Agba coder");
        createNoteRequest.setContent("He is agba coder");
        createNoteRequest.setEmail(registerUserRequest.getEmail());
        createNoteRequest.setDateCreated(LocalDateTime.now());

        Tags tags = new Tags();
        tags.setName("programming");
        createNoteRequest.setTagName(tags);

        CreateNoteResponse response1 = userService.createNote(createNoteRequest);
        assertThat(response1.getMessage()).isNotNull();
        assertEquals(1, userService.getAllNote(registerUserRequest.getEmail()).size());


        FindAllNoteRequest findAllNoteRequest = new FindAllNoteRequest();
        findAllNoteRequest.setEmail(registerUserRequest.getEmail());
        List<FindAllNoteResponse> userNotes = userService.findAllNotesByEmail(findAllNoteRequest);

        assertThat(userNotes).isNotEmpty();

        assertEquals(1, userNotes.size());

        LockNoteRequest lockNoteRequest = new LockNoteRequest();
        lockNoteRequest.setPassword("1234");
        lockNoteRequest.setEmail(findAllNoteRequest.getEmail());
        LockNoteResponse lockNoteResponse = userService.lockNote(lockNoteRequest);
        assertTrue(lockNoteResponse.isLockStatus());

    }

    @Test
    public void testThatNoteCannotBeDeletedWithoutBeingCreatedThrowNoteNotFoundException() {
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setEmail("aruwa@gmail.com");
        registerUserRequest.setUsername("aruwa");
        registerUserRequest.setPassword("aRuwa121@");
        RegisterUserResponse registerUserResponse = userService.register(registerUserRequest);
        assertThat(registerUserResponse.getMessage()).isNotNull();
        assertEquals(1, userService.findAllUser().size());

        LoginUserRequest loginUserRequest = new LoginUserRequest();
        loginUserRequest.setEmail(registerUserRequest.getEmail());
        loginUserRequest.setPassword(registerUserRequest.getPassword());
        LoginUserResponse response = userService.login(loginUserRequest);
        assertThat(response.getMessage()).isNotNull();
        assertTrue(userService.findUserById(registerUserResponse.getUserId()).isLoginStatus());


        DeleteNoteRequest deleteNoteRequest = new DeleteNoteRequest();
        deleteNoteRequest.setEmail(registerUserRequest.getEmail());
        assertThrows(NoteNotFoundException.class, () -> userService.deleteNote(deleteNoteRequest));


    }

    @Test
    public void testThatUserNotFoundNoteCannotBeDeleted(){
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setEmail("bally@gmail.com");
        registerUserRequest.setUsername("bally");
        registerUserRequest.setPassword("Bally12$");
        RegisterUserResponse registerUserResponse = userService.register(registerUserRequest);
        assertThat(registerUserResponse.getMessage()).isNotNull();
        assertEquals(1, userService.findAllUser().size());


        LoginUserRequest loginUserRequest = new LoginUserRequest();
        loginUserRequest.setEmail(registerUserRequest.getEmail());
        loginUserRequest.setPassword(registerUserRequest.getPassword());
        LoginUserResponse response = userService.login(loginUserRequest);
        assertThat(response.getMessage()).isNotNull();
        assertTrue(userService.findUserById(registerUserResponse.getUserId()).isLoginStatus());


        RegisterUserRequest registerUserRequest1 = new RegisterUserRequest();
        registerUserRequest1.setEmail("inu@gmail.com");
        registerUserRequest1.setUsername("inu");
        registerUserRequest1.setPassword("Inu1@13q");
        RegisterUserResponse registerUserResponse1 = userService.register(registerUserRequest1);
        assertThat(registerUserResponse1.getMessage()).isNotNull();
        assertEquals(2, userService.findAllUser().size());


        LoginUserRequest loginUserRequest1 = new LoginUserRequest();
        loginUserRequest1.setEmail(registerUserRequest1.getEmail());
        loginUserRequest1.setPassword(registerUserRequest1.getPassword());
        LoginUserResponse response1 = userService.login(loginUserRequest1);
        assertThat(response1.getMessage()).isNotNull();
        assertTrue(userService.findUserById(registerUserResponse.getUserId()).isLoginStatus());


        CreateNoteRequest createNoteRequest = new CreateNoteRequest();
        createNoteRequest.setTitle("loop and if statement");
        createNoteRequest.setContent("It is in chapter 4");
        createNoteRequest.setEmail(registerUserRequest1.getEmail());
        createNoteRequest.setDateCreated(LocalDateTime.now());

        Tags tags = new Tags();
        tags.setName("java");
        createNoteRequest.setTagName(tags);

        CreateNoteResponse response2 = userService.createNote(createNoteRequest);
        assertThat(response2.getMessage()).isNotNull();


        CreateNoteRequest createNoteRequest1 = new CreateNoteRequest();
        createNoteRequest1.setTitle("industrial design");
        createNoteRequest1.setContent("mr dapo class");
        createNoteRequest1.setEmail(registerUserRequest1.getEmail());
        createNoteRequest1.setDateCreated(LocalDateTime.now());

        Tags tags1 = new Tags();
        tags1.setName("javas");
        createNoteRequest1.setTagName(tags1);

        CreateNoteResponse response3 = userService.createNote(createNoteRequest1);
        assertThat(response3.getMessage()).isNotNull();

        assertEquals(2, userService.getAllNote(registerUserRequest.getEmail()).size());


        DeleteNoteRequest deleteNoteRequest = new DeleteNoteRequest();
        deleteNoteRequest.setEmail("samuel@gmail.com");
        deleteNoteRequest.setTitle("industrial design");
        assertThrows(UserNotFoundException.class,()->userService.deleteNote(deleteNoteRequest));

    }

    @Test
    public void testThatNoteNotBelongingAnotherUserCannotBeDeleted(){
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setEmail("bally@gmail.com");
        registerUserRequest.setUsername("bally");
        registerUserRequest.setPassword("Bally1#pa");
        RegisterUserResponse registerUserResponse = userService.register(registerUserRequest);
        assertThat(registerUserResponse.getMessage()).isNotNull();
        assertEquals(1, userService.findAllUser().size());


        LoginUserRequest loginUserRequest = new LoginUserRequest();
        loginUserRequest.setEmail(registerUserRequest.getEmail());
        loginUserRequest.setPassword(registerUserRequest.getPassword());
        LoginUserResponse response = userService.login(loginUserRequest);
        assertThat(response.getMessage()).isNotNull();
        assertTrue(userService.findUserById(registerUserResponse.getUserId()).isLoginStatus());


        RegisterUserRequest registerUserRequest1 = new RegisterUserRequest();
        registerUserRequest1.setEmail("inu@gmail.com");
        registerUserRequest1.setUsername("inu");
        registerUserRequest1.setPassword("Inu112@a");
        RegisterUserResponse registerUserResponse1 = userService.register(registerUserRequest1);
        assertThat(registerUserResponse1.getMessage()).isNotNull();
        assertEquals(2, userService.findAllUser().size());


        LoginUserRequest loginUserRequest1 = new LoginUserRequest();
        loginUserRequest1.setEmail(registerUserRequest1.getEmail());
        loginUserRequest1.setPassword(registerUserRequest1.getPassword());
        LoginUserResponse response1 = userService.login(loginUserRequest1);
        assertThat(response1.getMessage()).isNotNull();
        assertTrue(userService.findUserById(registerUserResponse1.getUserId()).isLoginStatus());


        CreateNoteRequest createNoteRequest = new CreateNoteRequest();
        createNoteRequest.setTitle("loop and if statement");
        createNoteRequest.setContent("It is in chapter 4");
        createNoteRequest.setEmail(registerUserRequest1.getEmail());
        createNoteRequest.setDateCreated(LocalDateTime.now());

        Tags tags = new Tags();
        tags.setName("java");
        createNoteRequest.setTagName(tags);

        CreateNoteResponse response2 = userService.createNote(createNoteRequest);
        assertThat(response2.getMessage()).isNotNull();


        CreateNoteRequest createNoteRequest1 = new CreateNoteRequest();
        createNoteRequest1.setTitle("industrial design");
        createNoteRequest1.setContent("mr dapo class");
        createNoteRequest1.setEmail(registerUserRequest1.getEmail());
        createNoteRequest1.setDateCreated(LocalDateTime.now());

        Tags tags1 = new Tags();
        tags1.setName("javas");
        createNoteRequest1.setTagName(tags1);

        CreateNoteResponse response3 = userService.createNote(createNoteRequest1);
        assertThat(response3.getMessage()).isNotNull();

        assertEquals(2, userService.getAllNote(registerUserRequest.getEmail()).size());


        DeleteNoteRequest deleteNoteRequest = new DeleteNoteRequest();
        deleteNoteRequest.setEmail(createNoteRequest.getEmail());
        deleteNoteRequest.setTitle("industrial design");
        assertThrows(NoteDoesNotBelongToUserException.class,()->userService.deleteNote(deleteNoteRequest));

    }

    @Test
    public void testNoteCannotBeCreatedWithoutLoggingIn(){

        RegisterUserRequest registerUserRequest1 = new RegisterUserRequest();
        registerUserRequest1.setEmail("inu@gmail.com");
        registerUserRequest1.setUsername("inu");
        registerUserRequest1.setPassword("Tinuade$%4");
        RegisterUserResponse registerUserResponse1 = userService.register(registerUserRequest1);
        assertThat(registerUserResponse1.getMessage()).isNotNull();
        assertEquals(1, userService.findAllUser().size());


        CreateNoteRequest createNoteRequest = new CreateNoteRequest();
        createNoteRequest.setTitle("loop and if statement");
        createNoteRequest.setContent("It is in chapter 4");
        createNoteRequest.setEmail(registerUserRequest1.getEmail());
        createNoteRequest.setDateCreated(LocalDateTime.now());

        Tags tags = new Tags();
        tags.setName("java");
        createNoteRequest.setTagName(tags);
        assertThrows(UserNotLoggedInException.class,()->userService.createNote(createNoteRequest));


    }

    @Test
    public void unlockNote() {
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setEmail("orisha@gmail.com");
        registerUserRequest.setUsername("orisha");
        registerUserRequest.setPassword("Orisha1%2");
        RegisterUserResponse registerUserResponse = userService.register(registerUserRequest);
        assertThat(registerUserResponse.getMessage()).isNotNull();
        assertEquals(1, userService.findAllUser().size());

        LoginUserRequest loginUserRequest = new LoginUserRequest();
        loginUserRequest.setEmail(registerUserRequest.getEmail());
        loginUserRequest.setPassword(registerUserRequest.getPassword());
        LoginUserResponse response = userService.login(loginUserRequest);
        assertThat(response.getMessage()).isNotNull();
        assertTrue(userService.findUserById(registerUserResponse.getUserId()).isLoginStatus());


        CreateNoteRequest createNoteRequest = new CreateNoteRequest();
        createNoteRequest.setTitle("Agba coder");
        createNoteRequest.setContent("He is agba coder");
        createNoteRequest.setEmail(registerUserRequest.getEmail());
        createNoteRequest.setDateCreated(LocalDateTime.now());

        Tags tags = new Tags();
        tags.setName("programming");
        createNoteRequest.setTagName(tags);

        CreateNoteResponse response1 = userService.createNote(createNoteRequest);
        assertThat(response1.getMessage()).isNotNull();
        assertEquals(1, userService.getAllNote(registerUserRequest.getEmail()).size());


        FindAllNoteRequest findAllNoteRequest = new FindAllNoteRequest();
        findAllNoteRequest.setEmail(registerUserRequest.getEmail());
        List<FindAllNoteResponse> userNotes = userService.findAllNotesByEmail(findAllNoteRequest);

        assertThat(userNotes).isNotEmpty();

        assertEquals(1, userNotes.size());

        LockNoteRequest lockNoteRequest = new LockNoteRequest();
        lockNoteRequest.setPassword("1234");
        lockNoteRequest.setEmail(findAllNoteRequest.getEmail());
        LockNoteResponse lockNoteResponse = userService.lockNote(lockNoteRequest);
        assertTrue(lockNoteResponse.isLockStatus());

        UnlockNoteRequest unlockNoteRequest = new UnlockNoteRequest();
        unlockNoteRequest.setPassword("1234");
        unlockNoteRequest.setEmail(findAllNoteRequest.getEmail());
        UnlockNoteResponse unlockNoteResponse = userService.unlockNote(unlockNoteRequest);
        assertFalse(unlockNoteResponse.isLockStatus());

    }

    @Test
    public void testThatEmailFormatMustBeCorrectWhenRegistering_elseThrowInvalidEmailException(){
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setEmail("orishagmail.com");
        registerUserRequest.setUsername("orisha");
        registerUserRequest.setPassword("Orisha1%2");
        assertThrows(InvalidEmailFormat.class,()-> userService.register(registerUserRequest));
    }

    @Test
    public void testThatPasswordFormatIsInvalid_throwInavlidPasswordFormatException(){
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setEmail("orisha@gmail.com");
        registerUserRequest.setUsername("orisha");
        registerUserRequest.setPassword("Orisha");
        assertThrows(InvalidPasswordFormatException.class,()-> userService.register(registerUserRequest));
    }
}

