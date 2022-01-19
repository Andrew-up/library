package com.netcracker.ageev.library.controller;

import com.netcracker.ageev.library.dto.AgeLimitDTO;
import com.netcracker.ageev.library.dto.BooksDTO;
import com.netcracker.ageev.library.facade.BooksFacade;
import com.netcracker.ageev.library.model.books.AgeLimit;
import com.netcracker.ageev.library.model.books.Books;
import com.netcracker.ageev.library.service.BooksService;
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
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
@CrossOrigin
@PermitAll()
public class BooksController {


    private BooksService booksService;
    private BooksFacade booksFacade;
    private ResponseErrorValidator responseErrorValidator;

    @Autowired
    public BooksController(BooksService booksService, BooksFacade booksFacade, ResponseErrorValidator responseErrorValidator) {
        this.booksService = booksService;
        this.booksFacade = booksFacade;
        this.responseErrorValidator = responseErrorValidator;
    }

    @GetMapping("/all")
    public ResponseEntity<List<BooksDTO>> getAllBooks() {
        List<BooksDTO> booksDTOS = booksService.getAllBooks()
                .stream()
                .map(booksFacade::booksDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(booksDTOS, HttpStatus.OK);
    }


    @PostMapping("/create")
    public ResponseEntity<Object> createBook(@Valid @RequestBody BooksDTO booksDTO,
                                             BindingResult bindingResult, Principal principal) {
        ResponseEntity<Object> listError = responseErrorValidator.mappedValidatorService(bindingResult);
        if (!ObjectUtils.isEmpty(listError)) return listError;
        Books books = booksService.createBook(booksDTO, principal);
        BooksDTO booksDTO1 = booksFacade.booksDTO(books);
        return new ResponseEntity<>(booksDTO1, HttpStatus.OK);
    }


}
