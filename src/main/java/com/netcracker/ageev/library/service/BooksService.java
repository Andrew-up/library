package com.netcracker.ageev.library.service;

import com.netcracker.ageev.library.dto.BooksDTO;
import com.netcracker.ageev.library.model.books.Books;
import com.netcracker.ageev.library.model.users.Users;
import com.netcracker.ageev.library.repository.books.BooksRepository;
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

@Service
public class BooksService {

    public static final Logger LOG = LoggerFactory.getLogger(BooksService.class);
    private final BooksRepository booksRepository;
    private final UsersRepository usersRepository;

    private final AuthorsService authorsService;

    @Autowired
    public BooksService(BooksRepository booksRepository, UsersRepository usersRepository, AuthorsService authorsService) {
        this.booksRepository = booksRepository;
        this.usersRepository = usersRepository;

        this.authorsService = authorsService;
    }


    public List<Books> getAllBooks() {
        return booksRepository.findAllByOrderById();
    }


    public Books createBook(BooksDTO booksDTO, Principal principal) {
        Users users = getUserByPrincipal(principal);
        Books books = new Books();
        ArrayList<String> arrayListError =  isBookCorrect(booksDTO);
        if (!ObjectUtils.isEmpty(arrayListError)){
            books.setBookTitle(arrayListError.toString());
            return books;
        }
        books.setBookTitle(booksDTO.getBookTitle());
//        System.out.println("testtttttt"+authorsService.getAuthorsById(booksDTO.getAuthors()));
//        books.setAuthors(authorsService.getAuthorsById(booksDTO.getAuthors()));
        books.setAuthors(authorsService.getAuthorsById(booksDTO.getAuthors()));
//        books.setAgeLimitCode(booksDTO.get);
//        books.setGenreCode(booksDTO.getGenreCode());
        books.setReleaseDate(booksDTO.getBookReleaseDate());
        books.setNumberPages(booksDTO.getNumberPages());
        return booksRepository.save(books);
    }

    public Users getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return usersRepository.findUsersByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username" + username));
    }

    private ArrayList<String> isBookCorrect(BooksDTO booksDTO){
        ArrayList<String> listError = new ArrayList<>();
        if(booksDTO.getBookTitle().equals("null")){
            listError.add("Имя книги не корректно");
        }
        if(booksDTO.getAuthors()==null){
            listError.add("Автор не корректен");
        }
        if(booksDTO.getGenreCode().equals("null")){
            listError.add("Жанр не корректен");
        }
        if(booksDTO.getBookReleaseDate().equals("null")){
            listError.add("Дата релиза не корректна");
        }
        if(booksDTO.getPublisherId()==null){
            listError.add("Издательство не корректен");
        }
        if(booksDTO.getNumberPages()==null){
            listError.add("кол-во страниц не корректно");
        }
        if(booksDTO.getBookSeries()==null){
            listError.add("Серия не корректна");
        }
        if(booksDTO.getNameISBN().equals("null")){
            listError.add("ISBN не корректен");
        }
        if(booksDTO.getAgeLimitCode()==null){
            listError.add("Возрастное ограничение не корректено");
        }
        if(booksDTO.getLanguageId()==null){
            listError.add("Язык издания не корректен");
        }
        if(booksDTO.getTranslation().equals("null")){
            listError.add("Автор перевода не корректен");
        }
        return listError;
    }

}
