package com.netcracker.ageev.library.service;

import com.netcracker.ageev.library.dto.CoverBookDTO;
import com.netcracker.ageev.library.exception.DataNotFoundException;
import com.netcracker.ageev.library.model.books.CoverBook;
import com.netcracker.ageev.library.repository.books.CoverBookRepository;
import com.netcracker.ageev.library.repository.users.UsersRepository;
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
public class CoverBookService {

    public static final Logger LOG = LoggerFactory.getLogger(CoverBook.class);

    private final CoverBookRepository coverBookRepository;
    private final UsersRepository usersRepository;
    private final UsersService usersService;

    @Autowired
    public CoverBookService(CoverBookRepository coverBookRepository, UsersRepository usersRepository, UsersService usersService) {
        this.coverBookRepository = coverBookRepository;
        this.usersRepository = usersRepository;
        this.usersService = usersService;
    }

    public List<CoverBook> getAllCoverBook() {
        return coverBookRepository.findAllByOrderById();
    }

    public CoverBook createCoverBook(CoverBookDTO coverBookDTO, Principal principal) {

        CoverBook coverBook = new CoverBook();
        ArrayList<String> arrayListError = isCoverBookCorrect(coverBookDTO);
        if (!ObjectUtils.isEmpty(arrayListError)) {
            coverBook.setName(arrayListError.toString());
            return coverBook;
        }

        coverBook.setName(coverBookDTO.getCoverBookName());
        coverBookRepository.save(coverBook);
        return coverBook;


    }

    public CoverBook updateCoverBook(CoverBookDTO coverBookDTO, Principal principal) {

        ArrayList<String> arrayListError = isCoverBookCorrect(coverBookDTO);
        CoverBook coverBook = coverBookRepository.findCoverBookById(coverBookDTO.getCoverBookId()).orElseThrow(() -> new DataNotFoundException("Age Limit not found. updateCoverBook: "+ coverBookDTO.getCoverBookId()));
        if (!ObjectUtils.isEmpty(arrayListError)) {
            coverBook.setName(arrayListError.toString());
            return coverBook;
        }
        coverBook.setName(coverBookDTO.getCoverBookName());
        coverBookRepository.save(coverBook);
        return coverBook;

    }

    public String deleteGenre(Integer coverBookId, Principal principal) {

        Optional<CoverBook> delete = coverBookRepository.findCoverBookById(coverBookId);
        if (delete.isPresent()){
            delete.ifPresent(coverBookRepository::delete);
            return "Тип обложки с id: " + coverBookId + " удалено";
        }
        else {
            LOG.error("deleteGenre error. not found: "+ coverBookId );
            return "error delete.";
        }
    }


    private ArrayList<String> isCoverBookCorrect(CoverBookDTO coverBookDTO) {
        ArrayList<String> listError = new ArrayList<>();
        String regex = "^([0-9])?([а-яА-Яa-zA-Z]+\\s*,*\\.*[а-яА-Яa-zA-Z]*){1,5}$";
        boolean result = coverBookDTO.getCoverBookName().matches(regex);
        if (!result) {
            LOG.error("The expression did not pass the record format check. isGenreCorrect: "+ coverBookDTO.getCoverBookName());
            listError.add("Выражение не прошло проверку по формату записи");
        }
        return listError;
    }

    public CoverBook getCoverBookById(Integer id) {
        try {
            return coverBookRepository.findCoverBookById(id).orElseThrow(() -> new DataNotFoundException("not found. getCoverBookById: "+id));
        } catch (DataNotFoundException e) {
            return null;
        }
    }


}
