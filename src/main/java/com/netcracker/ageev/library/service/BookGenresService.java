package com.netcracker.ageev.library.service;

import com.netcracker.ageev.library.dto.BookGenresDTO;
import com.netcracker.ageev.library.model.books.BookGenres;
import com.netcracker.ageev.library.model.books.Books;
import com.netcracker.ageev.library.repository.books.BookGenresRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookGenresService {

    public static final Logger LOG = LoggerFactory.getLogger(BookGenresService.class);

    private final BookGenresRepository bookGenresRepository;

    @Autowired
    public BookGenresService(BookGenresRepository bookGenresRepository) {
        this.bookGenresRepository = bookGenresRepository;
    }

    public List<BookGenres> getAllBookGenres() {
        return bookGenresRepository.findAllByOrderByBookGenresId();
    }

    public BookGenres createGenre(BookGenresDTO bookGenresDTO, Principal principal) {
        BookGenres bookGenres = new BookGenres();
        ArrayList<String> arrayListError = isGenreCorrect(bookGenresDTO);
        if (!ObjectUtils.isEmpty(arrayListError)) {
            bookGenres.setGenre(arrayListError.toString());
            return bookGenres;
        }
        bookGenres.setGenre(bookGenresDTO.getGenresName());
        return bookGenresRepository.save(bookGenres);
    }

    private ArrayList<String> isGenreCorrect(BookGenresDTO bookGenresDTO) {
        ArrayList<String> listError = new ArrayList<>();
        if (bookGenresDTO.getGenresName().isEmpty()) {
            listError.add("Имя жанра не корректно");
        }
        return listError;
    }

    public BookGenres getGenresById(Integer id) {
        try {
            return bookGenresRepository.findBookGenresByBookGenresId(id).orElseThrow(() -> new NullPointerException("not found"));
        } catch (NullPointerException e) {
            return null;
        }
    }

}
