package com.netcracker.ageev.library.facade;

import com.netcracker.ageev.library.dto.BooksDTO;
import com.netcracker.ageev.library.model.books.Books;
import com.netcracker.ageev.library.model.users.BasketUser;
import org.springframework.stereotype.Component;

@Component
public class BooksFacade {

    public BooksDTO booksDTO(Books books) {
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
        booksDTO.setAuthorsFullName(books.getFullName());
        booksDTO.setPublisherName(books.getPublisherId().getName());
        booksDTO.setCoverId(books.getCoverId().getId());
        booksDTO.setCoverName(books.getCoverId().getName());
        booksDTO.setAgeLimitCode(books.getAgeLimitCode().getId());
        booksDTO.setAgeLimitName(books.getAgeLimitCode().getAge());
        booksDTO.setLanguageId(books.getLanguageId().getLanguageId());
        booksDTO.setLanguageName(books.getLanguageId().getLanguageName());
        booksDTO.setPriceName(books.getPrice().getPriceRent().toString());
        booksDTO.setCountBooks(books.getCountBooks());
//        booksDTO.setRentalRequestToUserList(books.getRentalRequestToUser().stream().map(BasketUser::getUsers).map(Users::getId).collect(Collectors.toList()));
//        System.out.println("array22: "+books.getRentalRequestToUser().stream().map(BasketUser::getUsers).collect(Collectors.toList()));
//        System.out.println(books.getSeries().getSeriesName());
//        System.out.println(books.getSeries().getSeriesId());
//        System.out.println(books.getGenreCode().getGenre());

        return booksDTO;
    }

    public BooksDTO searchBooksDTO(Books books) {
        BooksDTO booksDTO = new BooksDTO();
        booksDTO.setBookId(books.getId());
        booksDTO.setBookTitle(books.getBookTitle());
        booksDTO.setNameISBN(books.getISBN());
        booksDTO.setNumberPages(books.getNumberPages());
        booksDTO.setAuthorsFullName(books.getFullName());
        booksDTO.setGenreName(books.getGenreCode().getGenre());
        booksDTO.setBookReleaseDate(books.getReleaseDate());
        booksDTO.setPublisherName(books.getPublisherId().getName());
        booksDTO.setCoverName(books.getCoverId().getName());
        booksDTO.setSeriesName(books.getSeries().getSeriesName());
        booksDTO.setAgeLimitName(books.getAgeLimitCode().getAge());
        booksDTO.setLanguageId(books.getLanguageId().getLanguageId());
        booksDTO.setLanguageName(books.getLanguageId().getLanguageName());
        booksDTO.setTranslationName(books.getTranslation().getTranslationName());
        booksDTO.setPriceName(books.getPrice().getPriceRent().toString());
        booksDTO.setCountBooks(books.getCountBooks());
        return booksDTO;
    }


    public BooksDTO booksDTO2(BasketUser books) {
        BooksDTO booksDTO = new BooksDTO();
        booksDTO.setBookId(books.getBooks().getId());
        booksDTO.setBookTitle(books.getBooks().getBookTitle());
        return  booksDTO;
    }

}
