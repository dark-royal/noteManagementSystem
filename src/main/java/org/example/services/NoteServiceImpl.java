package org.example.services;

import org.example.data.models.Notes;
import org.example.data.repositories.NoteRepository;
import org.example.dtos.request.CreateNoteRequest;
import org.example.dtos.request.DeleteNoteRequest;
import org.example.dtos.request.UpdateNotesRequest;
import org.example.dtos.responses.CreateNoteResponse;
import org.example.dtos.responses.UpdateNoteResponse;
import org.example.exceptions.NoteAlreadyExistException;
import org.example.exceptions.NoteNotFoundException;
import org.example.exceptions.NoteNotUpdatedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class NoteServiceImpl implements NoteService{

    @Autowired
    private NoteRepository noteRepository;
    @Override
    public CreateNoteResponse createNote(CreateNoteRequest createNoteRequest) {
        validateCreateNote(createNoteRequest.getContent(),createNoteRequest.getTitle());
        Notes note = new Notes();
        note.setTitle(createNoteRequest.getTitle());
        note.setContent(createNoteRequest.getContent());
        note.setDateAndTimeCreated(createNoteRequest.getDateCreated());
        noteRepository.save(note);

        CreateNoteResponse createNoteResponse = new CreateNoteResponse();
        createNoteResponse.setMessage("create note successfully");
        return createNoteResponse;
    }

    private void validateCreateNote(String content, String title) {
        Notes note = noteRepository.findByContentAndTitle(content,title);
        if(note != null) throw new NoteAlreadyExistException("note exist already");

    }

    @Override
    public List<Notes> findAll() {
        return noteRepository.findAll();
    }

    @Override
    public void deleteNote(DeleteNoteRequest deleteNoteRequest) {
        Notes notes = noteRepository.findNotesByTitle(deleteNoteRequest.getTitle());
        if(notes == null)throw new NoteNotFoundException("Note not found");
        else{
            noteRepository.delete(notes);
        }

    }

    @Override
    public UpdateNoteResponse updateNote(UpdateNotesRequest updateNoteRequest) {
        Notes notes = noteRepository.findNotesById(updateNoteRequest.getId());
        if (notes == null) throw new NoteNotFoundException("note not found");
        if (notes.getTitle().equalsIgnoreCase(updateNoteRequest.getTitle() ) && notes.getContent().equalsIgnoreCase(updateNoteRequest.getContent()))
            throw new NoteNotUpdatedException("note was not updated");
        else {
            if(notes.getId().equalsIgnoreCase(updateNoteRequest.getId()));
            notes.setTitle(updateNoteRequest.getTitle());
            notes.setContent(updateNoteRequest.getContent());
            notes.setDateAndTimeCreated(updateNoteRequest.getNewDateCreated());
            noteRepository.save(notes);
            UpdateNoteResponse updateNoteResponse = new UpdateNoteResponse();
            updateNoteResponse.setMessage("note updated successfully");
            return updateNoteResponse;
        }
    }
}
