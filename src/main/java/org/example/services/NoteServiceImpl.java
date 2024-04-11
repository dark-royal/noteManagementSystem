package org.example.services;

import org.example.data.models.Notes;
import org.example.data.repositories.NoteRepository;
import org.example.dtos.request.CreateNoteRequest;
import org.example.dtos.request.DeleteNoteRequest;
import org.example.dtos.request.FindNoteRequest;
import org.example.dtos.request.UpdateNotesRequest;
import org.example.dtos.responses.CreateNoteResponse;
import org.example.dtos.responses.UpdateNoteResponse;
import org.example.exceptions.NoteAlreadyExistException;
import org.example.exceptions.NoteNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class NoteServiceImpl implements NoteService {

    @Autowired
    private NoteRepository noteRepository;

    @Override
    public CreateNoteResponse createNote(CreateNoteRequest createNoteRequest) {
        validateCreateNote(createNoteRequest.getContent(), createNoteRequest.getTitle());
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
        Notes note = noteRepository.findByContentAndTitle(content, title);
        if (note != null) throw new NoteAlreadyExistException("note exist already");

    }

    @Override
    public List<Notes> findAll() {
        return noteRepository.findAll();
    }

    @Override
    public void deleteNote(DeleteNoteRequest deleteNoteRequest) {
        Notes notes = noteRepository.findNotesByTitle(deleteNoteRequest.getTitle());
        if (notes == null) throw new NoteNotFoundException("Note not found");
        else {
            noteRepository.delete(notes);
        }

    }

    @Override
    public UpdateNoteResponse updateNote(UpdateNotesRequest updateNoteRequest, String id) {
        Optional<Notes> notes = noteRepository.findById(id);
        if (notes.isPresent()){
            Notes notes1 = notes.get();
            notes1.setTitle(updateNoteRequest.getTitle());
            notes1.setContent(updateNoteRequest.getContent());
            notes1.setDateAndTimeCreated(updateNoteRequest.getNewDateCreated());

            UpdateNoteResponse updateNoteResponse = new UpdateNoteResponse();
            updateNoteResponse.setMessage("note updated successfully");
            return updateNoteResponse;
        }
        else {
            throw new NoteNotFoundException("note not found");
        }
    }

    @Override
    public List<Notes> getAllNote() {
        return noteRepository.findAll();
    }

    @Override
    public Notes findNote(FindNoteRequest findNoteRequest) {
        Notes notes = noteRepository.findNotesByTitle(findNoteRequest.getTitle());
        if(notes == null)throw new NoteNotFoundException("Note not found");
        else {
            return notes;
        }

    }

}
