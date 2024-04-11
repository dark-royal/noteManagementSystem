package org.example.data.repositories;

import org.example.data.models.Notes;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface NoteRepository extends MongoRepository<Notes,String> {

    Notes findByContentAndTitle(String content, String title);


    Notes findNotesByTitle(String title);

    Optional<Notes> findNotesById(String id);

}
