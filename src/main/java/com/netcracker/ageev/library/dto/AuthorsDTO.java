package com.netcracker.ageev.library.dto;

import com.netcracker.ageev.library.model.books.Books;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
public class AuthorsDTO {
    private Integer authorsId;
    private String firstname;
    private String lastname;
    private String patronymic;
    private String dateOfBirth;
//    private List<Books> books;
//    private List<Books> booksString;
}
