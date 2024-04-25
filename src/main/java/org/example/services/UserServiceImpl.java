package org.example.services;

import org.example.data.models.Notes;
import org.example.data.models.Tags;
import org.example.data.models.User;
import org.example.data.repositories.NoteRepository;
import org.example.data.repositories.TagRepository;
import org.example.data.repositories.UserRepository;
import org.example.dtos.request.*;
import org.example.dtos.responses.*;
import org.example.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service

public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NoteRepository noteRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private NoteService noteService;

    @Override
    public List<User> findAllUser() {
        return userRepository.findAll();
    }

    @Override
    public void lockNote(String password) {

    }

    @Override
    public RegisterUserResponse register(RegisterUserRequest registerUserRequest) {
        validateUser(registerUserRequest.getEmail());
        User user = new User();
        user.setUsername(registerUserRequest.getUsername());
        user.setPassword(registerUserRequest.getPassword());
        user.setEmail(registerUserRequest.getEmail());
        var savedUser = userRepository.save(user);

        RegisterUserResponse response = new RegisterUserResponse();
        response.setMessage("Registered successfully");
        response.setEmail(registerUserRequest.getEmail());
        response.setUsername(registerUserRequest.getUsername());
        response.setUserId(savedUser.getId());
        return response;
    }

    @Override
    public void deleteAll() {
        userRepository.deleteAll();
    }

    @Override
    public LoginUserResponse login(LoginUserRequest loginUserRequest) {

        User user = userRepository.findUserByEmail(loginUserRequest.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!user.getPassword().equals(loginUserRequest.getPassword())) {
            throw new InvalidPasswordException("Invalid password");
        }

        user.setLoginStatus(true);
        userRepository.save(user);

        LoginUserResponse loginUserResponse = new LoginUserResponse();
        loginUserResponse.setMessage("Login successful");
        loginUserResponse.setLoginStatus(user.isLoginStatus());
        return loginUserResponse;
    }


    @Override
    public User findUserById(String id) {
        return userRepository.findById(id).
                orElseThrow(()-> new UserNotFoundException("user not found"));
    }

    @Override
    public LogoutUserResponse logout(LogoutUserRequest logoutUserRequest) {
        validateLogin(logoutUserRequest.getEmail());
        User user = userRepository.findUserByEmail(logoutUserRequest.getEmail()).orElseThrow(()-> new UserNotFoundException("User not found"));
            user.setLoginStatus(false);
            userRepository.save(user);
            LogoutUserResponse logoutUserResponse = new LogoutUserResponse();
            logoutUserResponse.setMessage("logout successfully");
            return logoutUserResponse;
    }

    @Override
    public List<Notes> getAllNote(String email) {
       //List<Notes> notesList = this.noteService.findAllUserNote(userRepository.findUserByEmail(email).get());
//        return noteRepository.findAll();
//        Optional<User> userOptional = userRepository.findUserByEmail(email);
//        if (userOptional.isPresent()) {
//            User user = userOptional.get();
//            List<Notes> notesList = noteService.findAllUserNote(user);
//            return notesList;
//        } else {
//            throw new UserNotFoundException("user not found");
//
//        }
        return noteRepository.findAll();
    }

    @Override
    public List<Tags> getAllTags() {
        return tagRepository.findAll();
    }



    @Override
    public CreateNoteResponse createNote(CreateNoteRequest createNoteRequest) {
        validateLogin(createNoteRequest.getEmail());
        User user = userRepository.findUserByEmail(createNoteRequest.getEmail()).get();

        Notes note = new Notes();

        note.setTitle(createNoteRequest.getTitle());
        note.setContent(createNoteRequest.getContent());
        note.setDateCreated(createNoteRequest.getDateCreated());
        note.setUser(user);
        Notes savedNote = noteRepository.save(note);

        Tags tags = new Tags();
        tags.setName(createNoteRequest.getTagName().getName());
        tagRepository.save(tags);
        if (user.getNotesList() == null) {
            user.setNotesList(new ArrayList<>());
        }
        user.getNotesList().add(savedNote);
        userRepository.save(user);


        CreateNoteResponse createNoteResponse = new CreateNoteResponse();
        createNoteResponse.setMessage("Create note successfully");
        createNoteResponse.setTitle(createNoteRequest.getTitle());
        createNoteResponse.setEmail(createNoteRequest.getEmail());
        createNoteResponse.setContent(createNoteRequest.getContent());
        createNoteResponse.setDateCreated(LocalDateTime.now());
        createNoteResponse.setId(savedNote.getId());
        return createNoteResponse;
    }


    @Override
    public DeleteNoteResponse deleteNote(DeleteNoteRequest deleteNoteRequest) {
        Notes noteToDelete = noteRepository.findByTitle(deleteNoteRequest.getTitle());

        if (noteToDelete == null) {
            DeleteNoteResponse response = new DeleteNoteResponse();
            response.setMessage("Note with title '" + deleteNoteRequest.getTitle() + "' not found");
            return response;
        }

        User user = userRepository.findUserByEmail(deleteNoteRequest.getEmail()).orElse(null);
        if (user == null) {
            DeleteNoteResponse response = new DeleteNoteResponse();
            response.setMessage("User with email '" + deleteNoteRequest.getEmail() + "' not found");
            return response;
        }

        if (!noteToDelete.getUser().getEmail().equals(user.getEmail())) {
            DeleteNoteResponse response = new DeleteNoteResponse();
            response.setMessage("Note with title '" + deleteNoteRequest.getTitle() + "' does not belong to the user");
            return response;
        }


        noteRepository.delete(noteToDelete);
        DeleteNoteResponse response = new DeleteNoteResponse();
        response.setMessage("Deleted note successfully");
        return response;
    }




    @Override
    public List<Notes> findNoteByTagName(FindNoteRequest findNoteRequest) {
        List<Notes> foundNotes = new ArrayList<>();
        List<Tags> tagsList = getAllTags();

        for (Notes note : noteRepository.findAll()) {
            boolean containsTag = false;
            for (Tags tag : tagsList) {
                if (tag.getName() != null && tag.getName().equals(findNoteRequest.getTagName())) {
                    containsTag = true;
                    break;
                }
            }

            if (containsTag) {
                foundNotes.add(note);
            }
        }
        return foundNotes;
    }

    @Override
    public List<FindAllNoteResponse> findAllNotesByEmail(FindAllNoteRequest findAllNoteRequest) {
        List<Notes> userNotes = userRepository.findNotesByEmail(findAllNoteRequest.getEmail());

        if (userNotes != null && !userNotes.isEmpty()) {
            List<FindAllNoteResponse> responseList = new ArrayList<>();

            for (Notes note : userNotes) {
                FindAllNoteResponse response = convertToResponse(note);
                responseList.add(response);
            }

            return responseList;
        } else {
            throw new UserNoteListIsEmptyException("Note list is empty");
        }
    }

    private FindAllNoteResponse convertToResponse(Notes note) {
            FindAllNoteResponse response = new FindAllNoteResponse();

            if (note != null) {
                response.setId(note.getId());
                response.setTitle(note.getTitle());
                response.setContent(note.getContent());
                response.setDateCreated(note.getDateCreated());
            } else {
                throw new IllegalArgumentException("Note is null");
            }

            return response;
        }




    @Override
    public FindNoteResponse findNote(FindNoteRequest findNoteRequest){
        Optional<User> user = userRepository.findUserByEmail(findNoteRequest.getEmail());
        return null;
    }



    @Override
    public ShareNoteResponse shareNote(ShareNoteRequest shareNoteRequest) {
        User sendersEmail = userRepository.findUserByEmail(shareNoteRequest.getSenderEmail()).orElseThrow(()->new UserNotFoundException(String.format("%s not found", shareNoteRequest.getSenderEmail())));
        User receiverEmail = userRepository.findUserByEmail(shareNoteRequest.getSenderEmail()).orElseThrow(()->new UserNotFoundException(String.format("%s not found", shareNoteRequest.getSenderEmail())));
        Notes notes = noteRepository.findNotesById(shareNoteRequest.getId()).orElseThrow(()->new NoteNotFoundException("note not found"));

        List<Notes> sharedNotes = sendersEmail.getSharedNotesList();



        List<Notes> receiversNotes = sendersEmail.getReceiverReceivedNote();
        sharedNotes.add(notes);
        sendersEmail.setSharedNotesList(sharedNotes);
        sendersEmail.setSharedDate(LocalDateTime.now());
        sendersEmail.setNoteTitle(shareNoteRequest.getNoteTitle());
        sendersEmail.setNoteContent(shareNoteRequest.getNoteContent());
        userRepository.save(sendersEmail);

    ShareNoteResponse shareNoteResponse = new ShareNoteResponse();
    shareNoteResponse.setNoteShared();
    shareNoteResponse.setNoteTitle();
    shareNoteResponse.setDateShared();
    shareNoteResponse.setSenderEmail();
    shareNoteResponse.setReceiverEmail();
    return shareNoteResponse;
    }




    @Override
    public MakeSFavoriteResponse makeFavorites(MakeFavoritesRequest makeFavoritesRequest) {
        return null;
    }

    @Override
    public UpdateNoteResponse updateNote(UpdateNotesRequest updateNotesRequest){
        validateLogin(updateNotesRequest.getEmail());
        userRepository.findUserByEmail(updateNotesRequest.getEmail()).get();
        Optional<Notes> notes = noteRepository.findById(updateNotesRequest.getId());
        return getUpdateNoteResponse(updateNotesRequest, notes);

    }

    public static UpdateNoteResponse getUpdateNoteResponse(UpdateNotesRequest updateNotesRequest, Optional<Notes> notes) {

        if (notes.isPresent()){
            Notes notes1 = notes.get();
            notes1.setTitle(updateNotesRequest.getTitle());
            notes1.setContent(updateNotesRequest.getContent());
            notes1.setDateCreated(updateNotesRequest.getNewDateCreated());
            notes1.setTags(updateNotesRequest.getTagName());

            UpdateNoteResponse updateNoteResponse = new UpdateNoteResponse();
            updateNoteResponse.setMessage("note updated successfully");
            return updateNoteResponse;
        }
        else {
            throw new NoteNotFoundException("note not found");

        }
    }



    public void validateUser(String email){
        Optional<User> user = userRepository.findUserByEmail(email);
        if(user.isPresent()) throw new UserExistException(String.format("%s already exist", email));
    }

    public void validateLogin(String email){
       User user = userRepository.findUserByEmail(email).orElseThrow(()->new UserNotFoundException("user not found"));
        if(!user.isLoginStatus())throw new UserNotLoggedInException("Pls log in ");

    }
    private void validateUserExistence(String email) {
        if(userRepository.findUserByEmail(email).isEmpty())
            throw new UserNotFoundException("""
                    user not found
                    do you mean Dark royal???🤣🤣🤣🤣""");
    }


}
