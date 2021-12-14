package com.netcracker.ageev.library.service;

import com.netcracker.ageev.library.model.books.Authors;
import com.netcracker.ageev.library.repository.books.AuthorsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorsService {

    public static final Logger LOG = LoggerFactory.getLogger(BooksService.class);

    private AuthorsRepository authorsRepository;

    @Autowired
    public AuthorsService(AuthorsRepository authorsRepository) {
        this.authorsRepository = authorsRepository;
    }

    public List<Authors> getAllAuthors() {
        return authorsRepository.findAllByOrderById();
    }


}
