package com.netcracker.ageev.library.service;

import com.netcracker.ageev.library.entity.books.BookRent;
import com.netcracker.ageev.library.entity.books.Books;
import com.netcracker.ageev.library.entity.books.Price;
import com.netcracker.ageev.library.repository.books.BookRentRepository;
import com.netcracker.ageev.library.repository.books.BooksRepository;
import com.netcracker.ageev.library.repository.books.PriceRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;

import java.util.List;

@Service
public class PriceService {

    public static final Logger LOG = LoggerFactory.getLogger(PriceService.class);

    private final BookRentRepository bookRentRepository;
    private final PriceRepository priceRepository;

    @Autowired
    public PriceService(BookRentRepository bookRentRepository, PriceRepository priceRepository) {
        this.bookRentRepository = bookRentRepository;
        this.priceRepository = priceRepository;
    }

    public List<BookRent> getAllPrice(){
       return bookRentRepository.findAllByOrderByDateIssue();
    }

}
