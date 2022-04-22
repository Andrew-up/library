package com.netcracker.ageev.library.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthorsDTO {

    private Integer authorsId;
    private String firstname;
    private String lastname;
    private String patronymic;
    private String dateOfBirth;

}
