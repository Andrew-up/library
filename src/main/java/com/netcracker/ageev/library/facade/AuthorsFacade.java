package com.netcracker.ageev.library.facade;

import com.netcracker.ageev.library.dto.AuthorsDTO;
import com.netcracker.ageev.library.model.books.Authors;
import com.netcracker.ageev.library.model.books.Books;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class AuthorsFacade {

    public AuthorsDTO authorsDTO(Authors authors) {
        AuthorsDTO authorsDTO = new AuthorsDTO();
        authorsDTO.setAuthorsId(authors.getId());
        authorsDTO.setFirstname(authors.getFirstname());
        authorsDTO.setLastname(authors.getLastname());
        authorsDTO.setPatronymic(authors.getPatronymic());
        authorsDTO.setDateOfBirth(authors.getDateOfBirth());
//        authorsDTO.setBooks(authors.getBooks());
//        authorsDTO.setBooksString(authors.getAuthorsList());
        return authorsDTO;
    }
}
