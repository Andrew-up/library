package com.netcracker.ageev.library.facade;

import com.netcracker.ageev.library.dto.AuthorsDTO;
import com.netcracker.ageev.library.model.books.Authors;
import org.springframework.stereotype.Component;

@Component
public class AuthorsFacade {

    public AuthorsDTO authorsDTO(Authors authors){
        AuthorsDTO authorsDTO = new AuthorsDTO();
        authorsDTO.setFirstname(authors.getFirstname());
        authorsDTO.setLastname(authors.getLastname());
        authorsDTO.setPatronymic(authors.getPatronymic());
        authorsDTO.setDateOfBirth(authors.getDateOfBirth());
        authorsDTO.setBooks(authors.getBooks());
        return authorsDTO;
    }
}
