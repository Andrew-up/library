package com.netcracker.ageev.library.controller;

import com.netcracker.ageev.library.dto.AuthorsDTO;
import com.netcracker.ageev.library.dto.SearchDTO;
import com.netcracker.ageev.library.facade.AuthorsFacade;
import com.netcracker.ageev.library.model.books.Authors;
import com.netcracker.ageev.library.service.AuthorsService;
import com.netcracker.ageev.library.validators.ResponseErrorValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin
@PermitAll()
public class AuthorsController {

    private final AuthorsService authorsService;
    private final AuthorsFacade authorsFacade;
    private final ResponseErrorValidator responseErrorValidator;

    @Autowired
    public AuthorsController(AuthorsService authorsService,
                             AuthorsFacade authorsFacade,
                             ResponseErrorValidator responseErrorValidator) {
        this.authorsService = authorsService;
        this.authorsFacade = authorsFacade;
        this.responseErrorValidator = responseErrorValidator;
    }

    @GetMapping("/authors/all")
    public ResponseEntity<List<AuthorsDTO>> getAllAuthors() {
        List<AuthorsDTO> authorsDTOS = authorsService.getAllAuthors()
                .stream()
                .map(authorsFacade::authorsDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(authorsDTOS, HttpStatus.OK);
    }

    @PostMapping("/authors/search")
    public ResponseEntity<List<AuthorsDTO>> getAllAuthorsByInputSearch(@Valid @RequestBody SearchDTO search) {
        String inputSearch = search.getSearch().toLowerCase();
        System.out.println("Строка поиска:" + inputSearch);
        List<AuthorsDTO> authorsDTOS = authorsService.getAllAuthorsByInputSearch(inputSearch)
                .stream()
                .map(authorsFacade::authorsDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(authorsDTOS, HttpStatus.OK);
    }

    @PostMapping("staff/authors/create")
    public ResponseEntity<Object> createAuthor(@Valid @RequestBody AuthorsDTO authorsDTO, BindingResult bindingResult, Principal principal) {
        ResponseEntity<Object> listError = responseErrorValidator.mappedValidatorService(bindingResult);
        if (!ObjectUtils.isEmpty(listError)) return listError;
        Authors authors = authorsService.createAuthors(authorsDTO, principal);
        AuthorsDTO authorsDTO1 = authorsFacade.authorsDTO(authors);
        return new ResponseEntity<>(authorsDTO1, HttpStatus.OK);
    }

}
