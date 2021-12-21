package com.netcracker.ageev.library.facade;

import com.netcracker.ageev.library.dto.BookGenresDTO;
import com.netcracker.ageev.library.model.books.BookGenres;
import org.springframework.stereotype.Component;

@Component
public class BookGenresFacade {

    public BookGenresDTO bookGenresDTO(BookGenres bookGenres){
        BookGenresDTO bookGenresDTO = new BookGenresDTO();
        bookGenresDTO.setBookGenresId(bookGenres.getBookGenresId());
        bookGenresDTO.setGenresName(bookGenres.getGenre());
        return bookGenresDTO;
    }
}
