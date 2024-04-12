package org.example.services;

import org.example.data.models.Notes;
import org.example.dtos.request.CreateNoteRequest;
import org.example.dtos.request.DeleteNoteRequest;
import org.example.dtos.request.FindNoteRequest;
import org.example.dtos.request.UpdateNotesRequest;
import org.example.dtos.responses.CreateNoteResponse;
import org.example.dtos.responses.UpdateNoteResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NoteService {
    CreateNoteResponse createNote(CreateNoteRequest createNoteRequest);

    List<Notes> findAll();

    void deleteNote(DeleteNoteRequest deleteNoteRequest);

    //UpdateNoteResponse updateNote(UpdateNotesRequest updateNoteRequest);

    UpdateNoteResponse updateNote(UpdateNotesRequest updateNoteRequest, String id);

    List<Notes> getAllNote();

    Notes findNote(FindNoteRequest findNoteRequest);

    List<Notes> findNoteByTagName(FindNoteRequest findNoteRequest);

}
