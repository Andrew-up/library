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
        booksDTO.setBookSeries(books.getSeries());
        booksDTO.setNameISBN(books.getISBN());
        booksDTO.setTranslation(books.getTranslation());
        booksDTO.setGenreCode(books.getGenreCode().getGenre());
        booksDTO.setAuthors(books.getAuthors().getId());
//        try{
//            booksDTO.setAuthors(books.getAuthors().getId());
////        booksDTO.setAuthorsName(books.getAuthors());
//            booksDTO.setAuthorsName(books.getAuthors().getFirstname() +" "+ books.getAuthors().getLastname());
//        }
//        catch (NullPointerException e){
//            booksDTO.setAuthors(0);
//            booksDTO.setAuthorsName("Authors not found");
//        }

        return booksDTO;
    }
}
