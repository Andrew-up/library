package com.netcracker.ageev.library.service;

import com.netcracker.ageev.library.dto.AuthorsDTO;
import com.netcracker.ageev.library.model.books.AgeLimit;
import com.netcracker.ageev.library.model.books.Authors;
import com.netcracker.ageev.library.repository.books.AuthorsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorsService {

    public static final Logger LOG = LoggerFactory.getLogger(BooksService.class);

    private final AuthorsRepository authorsRepository;

    @Autowired
    public AuthorsService(AuthorsRepository authorsRepository) {
        this.authorsRepository = authorsRepository;
    }

    public List<Authors> getAllAuthors() {
        return authorsRepository.findAllByOrderById();
    }

    public Authors getAuthorsById(Integer id) {
        try {
            return authorsRepository.findAuthorsById(id).orElseThrow(() -> new NullPointerException("not found"));
        } catch (NullPointerException e) {
            return null;
        }
    }

    public Authors createAuthors(AuthorsDTO authorsDTO, Principal principal) {
        Authors authors = new Authors();
        ArrayList<String> arrayList = isAuthorsCorrect(authorsDTO);
        if (!arrayList.isEmpty()) {
            authors.setFirstname(arrayList.toString());
            return authors;
        }
        authors.setFirstname(authorsDTO.getFirstname());
        authors.setLastname(authorsDTO.getLastname());
        authors.setPatronymic(authorsDTO.getPatronymic());
        authors.setDateOfBirth(authorsDTO.getDateOfBirth());
        return authorsRepository.save(authors);
    }

    private ArrayList<String> isAuthorsCorrect(AuthorsDTO authorsDTO) {
        ArrayList<String> listError = new ArrayList<>();
        if (authorsDTO.getFirstname().isEmpty()) {
            listError.add("Имя не корректно");
        }
        if (authorsDTO.getLastname().isEmpty()) {
            listError.add("Фамилия не корректна");
        }
        if (authorsDTO.getPatronymic().isEmpty()) {
            listError.add("Отчество не корректно");
        }
        if (authorsDTO.getDateOfBirth().isEmpty()) {
            listError.add("Дата рождения не корректна");
        }
        return listError;
    }


}
