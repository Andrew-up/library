package com.netcracker.ageev.library.controller;

import com.netcracker.ageev.library.dto.BookGenresDTO;
import com.netcracker.ageev.library.facade.BookGenresFacade;
import com.netcracker.ageev.library.model.books.BookGenres;
import com.netcracker.ageev.library.service.BookGenresService;
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
@RequestMapping("/api/books/genres")
@CrossOrigin
public class BookGenresController {

    private BookGenresFacade bookGenresFacade;
    private BookGenresService bookGenresService;
    private ResponseErrorValidator responseErrorValidator;

    @Autowired
    public BookGenresController(BookGenresFacade bookGenresFacade, BookGenresService bookGenresService, ResponseErrorValidator responseErrorValidator) {
        this.bookGenresFacade = bookGenresFacade;
        this.bookGenresService = bookGenresService;
        this.responseErrorValidator = responseErrorValidator;
    }

    @GetMapping("/all")
    public ResponseEntity<List<BookGenresDTO>> getAllGenres() {
        List<BookGenresDTO> bookGenresDTOS = bookGenresService.getAllBookGenres()
                .stream()
                .map(bookGenresFacade::bookGenresDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(bookGenresDTOS, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createGenre(@Valid @RequestBody BookGenresDTO bookGenresDTO, BindingResult bindingResult, Principal principal){
        ResponseEntity<Object> listError = responseErrorValidator.mappedValidatorService(bindingResult);
        if (!ObjectUtils.isEmpty(listError)) return listError;
        BookGenres bookGenres = bookGenresService.createGenre(bookGenresDTO,principal);
        BookGenresDTO bookGenresDTO1 = bookGenresFacade.bookGenresDTO(bookGenres);
        return new ResponseEntity<>(bookGenresDTO1,HttpStatus.OK);
    }

}