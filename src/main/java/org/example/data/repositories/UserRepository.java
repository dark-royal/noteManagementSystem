package org.example.data.repositories;

import org.example.data.models.Notes;
import org.example.data.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User,String> {
    Optional<User> findUserByEmail(String email);


    List<Notes> findNotesByEmail(String email);

    Notes findNoteByEmailAndNoteTitle(String email, String title);
}
