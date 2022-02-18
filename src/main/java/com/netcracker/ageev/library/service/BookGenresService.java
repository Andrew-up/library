package com.netcracker.ageev.library.service;

import com.netcracker.ageev.library.dto.BookGenresDTO;
import com.netcracker.ageev.library.model.books.BookGenres;
import com.netcracker.ageev.library.model.books.Books;
import com.netcracker.ageev.library.repository.books.BookGenresRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookGenresService {

    public static final Logger LOG = LoggerFactory.getLogger(BookGenresService.class);

    private final BookGenresRepository bookGenresRepository;

    private final UsersService usersService;

    @Autowired
    public BookGenresService(BookGenresRepository bookGenresRepository,UsersService usersService) {
        this.bookGenresRepository = bookGenresRepository;
        this.usersService = usersService;
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

    public BookGenres updateGenre(BookGenresDTO bookGenresDTO){
        BookGenres bookGenres = bookGenresRepository.findBookGenresByBookGenresId(bookGenresDTO.getBookGenresId()).orElseThrow(() -> new UsernameNotFoundException("Genre not found" ));
        bookGenres.setGenre(bookGenresDTO.getGenresName());
        return bookGenresRepository.save(bookGenres);
    }

    public void deleteGenre(Integer bookId){
        Optional<BookGenres> delete = bookGenresRepository.findBookGenresByBookGenresId(bookId);
        delete.ifPresent(bookGenresRepository::delete);
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
