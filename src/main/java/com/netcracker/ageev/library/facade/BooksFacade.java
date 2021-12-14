package com.netcracker.ageev.library.facade;

import com.netcracker.ageev.library.dto.BooksDTO;
import com.netcracker.ageev.library.model.books.Books;
import org.springframework.stereotype.Component;

@Component
public class BooksFacade {

    public BooksDTO booksDTO(Books books){
        BooksDTO booksDTO = new BooksDTO();
        booksDTO.setBookTitle(books.getBookTitle());
        booksDTO.setReleaseDate(books.getBookTitle());
        booksDTO.setNumberPages(books.getNumberPages());
        booksDTO.setSeries(books.getSeries());
        booksDTO.setISBN(books.getISBN());
        booksDTO.setTranslation(books.getTranslation());
        booksDTO.setGenreCode(books.getGenreCode().getGenre());

        return booksDTO;
    }
}
