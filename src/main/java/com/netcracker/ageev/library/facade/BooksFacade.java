package com.netcracker.ageev.library.facade;

import com.netcracker.ageev.library.dto.BooksDTO;
import com.netcracker.ageev.library.model.books.Books;
import org.springframework.stereotype.Component;

@Component
public class BooksFacade {

    public BooksDTO booksDTO(Books books){
        BooksDTO booksDTO = new BooksDTO();
        booksDTO.setBookId(books.getId());
        booksDTO.setBookTitle(books.getBookTitle());
        booksDTO.setBookReleaseDate(books.getReleaseDate());
        booksDTO.setNumberPages(books.getNumberPages());
        booksDTO.setBookSeries(books.getSeries().getSeriesId());
        booksDTO.setNameISBN(books.getISBN());
        booksDTO.setTranslationId(books.getTranslation().getTranslationId());

        booksDTO.setGenreName(books.getGenreCode().getGenre());
        booksDTO.setAuthors(books.getAuthors().getId());
        booksDTO.setSeriesName(books.getSeries().getSeriesName());
        booksDTO.setTranslationName(books.getTranslation().getTranslationName());
//        System.out.println(books.getSeries().getSeriesName());
//        System.out.println(books.getSeries().getSeriesId());
//        System.out.println(books.getGenreCode().getGenre());
        return booksDTO;
    }
}
