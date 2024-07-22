package org.example.data.models;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;



@Getter
@Setter
@ToString
public class Notes {
   @Id
    private String id;
    private String title;
    private String email;
    private String content;
    private LocalDateTime dateCreated;
    private boolean lockStatus;
    private String password;
    private Tags tags;


}
