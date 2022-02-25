package com.netcracker.ageev.library.service;

import com.netcracker.ageev.library.dto.CoverBookDTO;
import com.netcracker.ageev.library.model.books.CoverBook;
import com.netcracker.ageev.library.model.users.Users;
import com.netcracker.ageev.library.repository.books.CoverBookRepository;
import com.netcracker.ageev.library.repository.users.UsersRepository;
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
        Users users = usersService.getUserByPrincipal(principal);
        CoverBook coverBook = new CoverBook();
        ArrayList<String> arrayListError = isCoverBookCorrect(coverBookDTO);
        if (!ObjectUtils.isEmpty(arrayListError)) {
            coverBook.setName(arrayListError.toString());
            return coverBook;
        }
        if (usersService.DataAccessToUser(users)) {
            coverBook.setName(coverBookDTO.getCoverBookName());
            coverBookRepository.save(coverBook);
            return coverBook;
        }
        else {
            coverBook.setName("Для пользователя с ролью " + users.getERole() + " создание невозможно");
            return coverBook;
        }
    }

    public CoverBook updateCoverBook(CoverBookDTO coverBookDTO, Principal principal) {
        Users users = usersService.getUserByPrincipal(principal);
        ArrayList<String> arrayListError = isCoverBookCorrect(coverBookDTO);
        CoverBook coverBook = coverBookRepository.findCoverBookById(coverBookDTO.getCoverBookId()).orElseThrow(() -> new UsernameNotFoundException("Age Limit not found"));
        if (!ObjectUtils.isEmpty(arrayListError)) {
            coverBook.setName(arrayListError.toString());
            return coverBook;
        }
        if (usersService.DataAccessToUser(users)) {
            coverBook.setName(coverBookDTO.getCoverBookName());
            coverBookRepository.save(coverBook);
            return coverBook;
        }
        else {
            coverBook.setName("Для пользователя с ролью " + users.getERole() + " обновление невозможно");
            return coverBook;
        }
    }

    public String deleteGenre(Integer coverBookId, Principal principal) {
        Users users = usersService.getUserByPrincipal(principal);
        if (usersService.DataAccessToUser(users)) {
            Optional<CoverBook> delete = coverBookRepository.findCoverBookById(coverBookId);
            delete.ifPresent(coverBookRepository::delete);
            return "Тип обложки с id: " + coverBookId + " удалено";
        } else {
            return "Для пользователя с ролью " + users.getERole() + " удаление невозможно";
        }
    }


    private ArrayList<String> isCoverBookCorrect(CoverBookDTO coverBookDTO) {
        ArrayList<String> listError = new ArrayList<>();
        String regex = "^([0-9])?([а-яА-Яa-zA-Z]*\\s*,*\\.*[а-яА-Яa-zA-Z]*){1,5}$";
        boolean result = coverBookDTO.getCoverBookName().matches(regex);
        if (!result) {
            listError.add("Выражение не прошло проверку по формату записи");
        }
        return listError;
    }

    public CoverBook getCoverBookById(Integer id) {
        try {
            return coverBookRepository.findCoverBookById(id).orElseThrow(() -> new NullPointerException("not found"));
        } catch (NullPointerException e) {
            return null;
        }
    }



}
