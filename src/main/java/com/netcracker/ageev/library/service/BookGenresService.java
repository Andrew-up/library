package com.netcracker.ageev.library.service;

import com.netcracker.ageev.library.dto.BookGenresDTO;
import com.netcracker.ageev.library.exception.DataNotFoundException;
import com.netcracker.ageev.library.model.books.BookGenres;
import com.netcracker.ageev.library.repository.books.BookGenresRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
    public BookGenresService(BookGenresRepository bookGenresRepository, UsersService usersService) {
        this.bookGenresRepository = bookGenresRepository;
        this.usersService = usersService;
    }

    public List<BookGenres> getAllBookGenres() {
        return bookGenresRepository.findAllByOrderByBookGenresId();
    }

    public BookGenres createGenre(BookGenresDTO bookGenresDTO, Principal principal) {

        BookGenres bookGenres = new BookGenres();

        return getBookGenres(bookGenresDTO, bookGenres);

    }

    // TODO: 22.02.2022
    //    -  Добавить свои исключения

    public BookGenres updateGenre(BookGenresDTO bookGenresDTO, Principal principal) {

        BookGenres bookGenres = bookGenresRepository.findBookGenresByBookGenresId(bookGenresDTO.getBookGenresId()).orElseThrow(() -> new DataNotFoundException("Genre not found. updateGenre"+ bookGenresDTO.getBookGenresId()));
        return getBookGenres(bookGenresDTO, bookGenres);

    }

    public void deleteGenre(Integer bookId) {
        Optional<BookGenres> delete = bookGenresRepository.findBookGenresByBookGenresId(bookId);
        delete.ifPresent(bookGenresRepository::delete);
        if (!delete.isPresent()){
            throw  new DataNotFoundException("Genre not found. deleteGenre: " +bookId);
        }
    }


    private BookGenres getBookGenres(BookGenresDTO bookGenresDTO, BookGenres bookGenres) {
        ArrayList<String> arrayListError = isGenreCorrect(bookGenresDTO);
        if (!ObjectUtils.isEmpty(arrayListError)) {
            bookGenres.setGenre(arrayListError.toString());
            return bookGenres;
        }
        try {
            bookGenres.setGenre(bookGenresDTO.getGenresName());
            bookGenresRepository.save(bookGenres);
            return bookGenres;
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            bookGenres.setBookGenresId(-2000);
            LOG.error("Error when adding to the database, such an entry already exists. getBookGenres: "+ bookGenresDTO.getGenresName());
            bookGenres.setGenre("Ошибка при добавлении в бд, такая запись уже есть");
            return bookGenres;
        }
    }

    private ArrayList<String> isGenreCorrect(BookGenresDTO bookGenresDTO) {
        ArrayList<String> listError = new ArrayList<>();
        String regex = "(^[a-zA-Zа-яА-Я]+)|((^[a-zA-Zа-яА-Я]+\\s[a-zA-Zа-яА-Я]+))";
        boolean result = bookGenresDTO.getGenresName().matches(regex);
        if (!result) {
            LOG.error("The expression did not pass the record format check. isGenreCorrect: "+ bookGenresDTO.getGenresName());
            listError.add("Выражение не прошло проверку по формату записи");
        }
        return listError;
    }

    public BookGenres getGenresById(Integer id) {
        try {
            return bookGenresRepository.findBookGenresByBookGenresId(id).orElseThrow(() -> new DataNotFoundException("not found. getGenresById: "+ id));
        } catch (DataNotFoundException e) {
            return null;
        }
    }


}
