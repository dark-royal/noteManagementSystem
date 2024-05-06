package org.example.data.repositories;

import org.example.data.models.Notes;
import org.example.data.models.Tags;
import org.example.data.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface NoteRepository extends MongoRepository<Notes,String> {




    Notes findNotesByTitle(String title);

    Optional<Notes> findNotesById(String id, User sendersEmail);


    Notes findByTitle(String title);

    List<Tags> findByTags(String tagName);

    Optional<List<Notes>> findNoteByUser(User note);
}

