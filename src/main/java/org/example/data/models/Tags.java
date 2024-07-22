package org.example.data.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;


@Getter
@Setter
public class Tags {
    @Id
    private String id;
    private String name;


}
