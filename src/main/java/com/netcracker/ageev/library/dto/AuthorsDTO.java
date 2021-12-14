package com.netcracker.ageev.library.dto;

import com.netcracker.ageev.library.model.books.Authors;
import com.netcracker.ageev.library.model.books.Books;
import lombok.Data;

import java.util.List;

@Data
public class AuthorsDTO {
    private String firstname;
    private String lastname;
    private String patronymic;
    private String dateOfBirth;
    private List<Books> books;
}
