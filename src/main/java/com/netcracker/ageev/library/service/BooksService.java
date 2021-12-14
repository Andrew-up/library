package com.netcracker.ageev.library.service;

import com.netcracker.ageev.library.model.books.Books;
import com.netcracker.ageev.library.repository.books.BooksRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BooksService {

    public static final Logger LOG = LoggerFactory.getLogger(BooksService.class);


    private final BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Books> getAllBooks(){
        return booksRepository.findAllByOrderById();
    }
}
