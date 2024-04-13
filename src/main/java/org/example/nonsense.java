package org.example;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
//public class nonsense {


//    CreateNoteRequest createNoteRequest = new CreateNoteRequest();
//        createNoteRequest.setTitle("Precious");
//        createNoteRequest.setContent("precious is a good girl i guess");
//        createNoteRequest.setDateCreated(LocalDateTime.now());
//
//    Tags tags = new Tags();
//            tags.setName("work");
//            notesList.add(tags);
//            createNoteRequest.setTagsList(notesList);






//        // Assuming you have a list of notes and tags in your UserService class
//        private List<Note> notesList;
//        private List<Tag> tagsList;
//
//        // Constructor and other methods here...
//
//        public void createNoteWithTags(String title, String content, List<String> tagNames) {
//            // Create a new note
//            Note newNote = new Note();
//            newNote.setTitle(title);
//            newNote.setContent(content);
//            newNote.setDateCreated(LocalDateTime.now());
//
//            // Create tags and add them to the note
//            List<Tag> noteTags = new ArrayList<>();
//            for (String tagName : tagNames) {
//                Tag tag = findOrCreateTag(tagName); // Find existing tag or create a new one
//                noteTags.add(tag);
//            }
//            newNote.setTagsList(noteTags);
//
//            // Add the note to the list of notes
//            notesList.add(newNote);
//        }
//
//        private Tag findOrCreateTag(String tagName) {
//            // Check if the tag already exists
//            for (Tag tag : tagsList) {
//                if (tag.getName().equals(tagName)) {
//                    return tag; // Return existing tag
//                }
//            }
//            // If the tag doesn't exist, create a new one
//            Tag newTag = new Tag(tagName);
//            tagsList.add(newTag); // Add the new tag to the list of tags
//            return newTag;
//        }
//
//
//    }
//
//
//
//
