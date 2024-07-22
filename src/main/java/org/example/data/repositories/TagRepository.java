package org.example.data.repositories;

import org.example.data.models.Tags;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface TagRepository extends MongoRepository<Tags,String> {
}
