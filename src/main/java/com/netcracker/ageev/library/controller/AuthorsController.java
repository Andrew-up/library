package com.netcracker.ageev.library.controller;

import com.netcracker.ageev.library.dto.AuthorsDTO;
import com.netcracker.ageev.library.facade.AuthorsFacade;
import com.netcracker.ageev.library.facade.BooksFacade;
import com.netcracker.ageev.library.service.AuthorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/authors")
@CrossOrigin
@PermitAll()
public class AuthorsController {

    private AuthorsService authorsService;
    private AuthorsFacade authorsFacade;

    @Autowired
    public AuthorsController(AuthorsService authorsService, AuthorsFacade authorsFacade) {
        this.authorsService = authorsService;
        this.authorsFacade = authorsFacade;
    }

    @GetMapping("/all")
    public ResponseEntity<List<AuthorsDTO>> getAllAuthors(){
        List<AuthorsDTO> authorsDTOS = authorsService.getAllAuthors()
                .stream()
                .map(authorsFacade::authorsDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(authorsDTOS, HttpStatus.OK);
    }


}
