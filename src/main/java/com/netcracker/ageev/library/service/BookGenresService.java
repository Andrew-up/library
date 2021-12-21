package com.netcracker.ageev.library.service;

import com.netcracker.ageev.library.model.books.BookGenres;
import com.netcracker.ageev.library.model.books.Books;
import com.netcracker.ageev.library.repository.books.BookGenresRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookGenresService {

    public static final Logger LOG = LoggerFactory.getLogger(BookGenresService.class);

    private final BookGenresRepository bookGenresRepository;

    @Autowired
    public BookGenresService(BookGenresRepository bookGenresRepository) {
        this.bookGenresRepository = bookGenresRepository;
    }

    public List<BookGenres> getAllBookGenres(){
        return bookGenresRepository.findAllByOrderByBookGenresId();
    }
}
