package com.netcracker.ageev.library.controller;

import com.netcracker.ageev.library.dto.BookGenresDTO;
import com.netcracker.ageev.library.facade.BookGenresFacade;
import com.netcracker.ageev.library.service.BookGenresService;
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
@RequestMapping("/api/genres")
@CrossOrigin
@PermitAll()
public class BookGenresController {

    private BookGenresFacade bookGenresFacade;
    private BookGenresService bookGenresService;

    @Autowired
    public BookGenresController(BookGenresFacade bookGenresFacade, BookGenresService bookGenresService) {
        this.bookGenresFacade = bookGenresFacade;
        this.bookGenresService = bookGenresService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<BookGenresDTO>> getAllGenres() {
        List<BookGenresDTO> bookGenresDTOS = bookGenresService.getAllBookGenres()
                .stream()
                .map(bookGenresFacade::bookGenresDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(bookGenresDTOS, HttpStatus.OK);
    }

}
