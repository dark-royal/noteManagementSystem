package org.example;

import org.example.data.models.Notes;
import org.example.data.repositories.NoteRepository;
import org.example.dtos.request.CreateNoteRequest;
import org.example.dtos.request.DeleteNoteRequest;
import org.example.dtos.request.UpdateNotesRequest;
import org.example.dtos.responses.CreateNoteResponse;
import org.example.dtos.responses.UpdateNoteResponse;
import org.example.exceptions.NoteAlreadyExistException;
import org.example.exceptions.NoteNotFoundException;
import org.example.services.NoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class NoteServiceImplTest {

    @Autowired
    private NoteService noteService;
    @Autowired
    private NoteRepository noteRepository;

    @BeforeEach
    public void setNoteService(){
        noteRepository.deleteAll();
    }

    @Test
    public void createNoteTest(){
        CreateNoteRequest createNoteRequest = new CreateNoteRequest();
        createNoteRequest.setTitle("My tomorrow");
        createNoteRequest.setContent("I know tomorrow will be good");
        createNoteRequest.setDateCreated(LocalDateTime.now());
        noteService.createNote(createNoteRequest);
        assertEquals(1,noteService.findAll().size());
    }

    @Test
    public void noteCannotBeCreatedWithTheSameDetails_ThrowNoteAlreadyExistException(){
        CreateNoteRequest createNoteRequest = new CreateNoteRequest();
        createNoteRequest.setTitle("My tomorrow");
        createNoteRequest.setContent("I know tomorrow will be good");
        createNoteRequest.setDateCreated(LocalDateTime.now());
        CreateNoteResponse response = noteService.createNote(createNoteRequest);
        assertThat(response.getMessage()).isNotNull();
        assertEquals(1,noteService.findAll().size());
        assertThrows(NoteAlreadyExistException.class,()->noteService.createNote(createNoteRequest));
    }

    @Test
    public void testNoteCanBeCreated_noteCanBeDeleted(){
        CreateNoteRequest createNoteRequest = new CreateNoteRequest();
        createNoteRequest.setTitle("My Love");
        createNoteRequest.setContent("I Love You So Much ");
        createNoteRequest.setDateCreated(LocalDateTime.now());
        noteService.createNote(createNoteRequest);
        assertEquals(1,noteService.findAll().size());

        DeleteNoteRequest deleteNoteRequest = new DeleteNoteRequest();
        deleteNoteRequest.setTitle("My Love");
        noteService.deleteNote(deleteNoteRequest);
        assertEquals(0,noteService.findAll().size());
    }


    @Test
    public void testThatNoteCannotBeDeletedWithoutCreatingIt_throwNoteNotFoundException(){
        DeleteNoteRequest deleteNoteRequest = new DeleteNoteRequest();
        deleteNoteRequest.setTitle("My Life");
        assertThrows(NoteNotFoundException.class,()->noteService.deleteNote(deleteNoteRequest));
    }

    @Test
    public void testThatNoteCanBeUpdated() {
        CreateNoteRequest createNoteRequest = new CreateNoteRequest();
        createNoteRequest.setTitle("My next week");
        createNoteRequest.setContent("I know tomorrow will be good and better");
        createNoteRequest.setDateCreated(LocalDateTime.now());
        CreateNoteResponse response = noteService.createNote(createNoteRequest);
        assertThat(response.getMessage()).isNotNull();
        assertEquals(1, noteService.findAll().size());

        UpdateNotesRequest updateNotesRequest = new UpdateNotesRequest();
        updateNotesRequest.setId(response.getId());
        updateNotesRequest.setTitle("My tomorrow my life");
        updateNotesRequest.setContent("i love this life");
        Notes updateNoteResponse = noteService.updateNote(updateNotesRequest, response.getId());
        assertNotNull(updateNoteResponse);
//        assertThat(updateNoteResponse.getMessage()).isNotNull();
        assertEquals(1,noteService.findAll().size());

    }

    @Test
    public void testThatAllBookCanBeGotten(){
        CreateNoteRequest createNoteRequest = new CreateNoteRequest();
        createNoteRequest.setTitle("My tomorrow");
        createNoteRequest.setContent("I know tomorrow will be good");
        createNoteRequest.setDateCreated(LocalDateTime.now());
        CreateNoteResponse response = noteService.createNote(createNoteRequest);
        assertThat(response.getMessage()).isNotNull();
        assertEquals(1, noteService.findAll().size());


        CreateNoteRequest createNoteRequest1 = new CreateNoteRequest();
        createNoteRequest1.setTitle("My today");
        createNoteRequest1.setContent("today is my day");
        createNoteRequest1.setDateCreated(LocalDateTime.now());
        CreateNoteResponse response1 = noteService.createNote(createNoteRequest);
        assertThat(response1.getMessage()).isNotNull();
        assertEquals(1, noteService.findAll().size());
        assertEquals(2,noteService.getAllNote().size());

    }

}
