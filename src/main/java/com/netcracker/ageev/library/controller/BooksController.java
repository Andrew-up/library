package com.netcracker.ageev.library.controller;

import com.netcracker.ageev.library.dto.AgeLimitDTO;
import com.netcracker.ageev.library.dto.BooksDTO;
import com.netcracker.ageev.library.facade.BooksFacade;
import com.netcracker.ageev.library.service.BooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;
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
    @Autowired
    public BooksController(BooksService booksService, BooksFacade booksFacade) {
        this.booksService = booksService;
        this.booksFacade = booksFacade;
    }


    @GetMapping("/all")
    public ResponseEntity<List<BooksDTO>> getAllBooks(){


        List<BooksDTO> booksDTOS = booksService.getAllBooks()
                .stream()
                .map(booksFacade::booksDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(booksDTOS,HttpStatus.OK);
    }

}
