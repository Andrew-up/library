package com.netcracker.ageev.library.service;

import com.netcracker.ageev.library.dto.BooksDTO;
import com.netcracker.ageev.library.model.books.Books;
import com.netcracker.ageev.library.model.users.BasketUser;
import com.netcracker.ageev.library.model.users.Users;
import com.netcracker.ageev.library.repository.books.BooksRepository;
import com.netcracker.ageev.library.repository.users.BasketUsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class BasketUsersService {
    public static final Logger LOG = LoggerFactory.getLogger(BasketUsersService.class);

    private BasketUsersRepository basketUsersRepository;
    private UsersService usersService;
    private BooksRepository booksRepository;

    @Autowired
    public BasketUsersService(BasketUsersRepository basketUsersRepository,
                              UsersService usersService,
                              BooksRepository booksRepository) {
        this.basketUsersRepository = basketUsersRepository;
        this.usersService = usersService;
        this.booksRepository = booksRepository;
    }

    public List<BasketUser> getAllBasket() {
        return this.basketUsersRepository.findAll();
    }

    public List<BasketUser> getAllBasketById(Long id) {
        return this.basketUsersRepository.findAllByUsersId(id);
    }

//    public List<BasketUser> getBasketUserByUsersIdAndTestsTrue(Long id) {
//        return this.basketUsersRepository.findBasketUserByUsersIdAndIsTheBasket(id);
//    }

    public List<BasketUser> getBasketToAllUsers(Long id) {
        return this.basketUsersRepository.findBasketUserByUsersIdAndIsTheBasketIsFalseAndIsRequestCreatedIsTrue(id);
    }

    public String cancelRequest(Long idRequest) {
        try {
            BasketUser basketUser = this.basketUsersRepository.findBasketUserByBasketUserId(idRequest).orElseThrow(() -> new UsernameNotFoundException("Books not found"));
            basketUser.setIsRequestCreated(false);
            basketUser.setIsTheBasket(true);
            basketUsersRepository.save(basketUser);
            return "Успешная отмена";
        } catch (UsernameNotFoundException e) {
            e.printStackTrace();
            return "Ошибка";
        }

    }

    public List<BasketUser> getAllBasketToUser(Long id) {
        return this.basketUsersRepository.findBasketUserByUsersIdAndIsTheBasketIsTrueAndIsRequestCreatedIsFalse(id);
    }

    public BasketUser addBasketToUser(BooksDTO booksDTO, Principal principal) {
        Users users = usersService.getUserByPrincipal(principal);
        Books books = booksRepository.findBooksById(booksDTO.getBookId()).orElseThrow(() -> new UsernameNotFoundException("Books not found"));
        BasketUser basketUser = new BasketUser();
        basketUser.setUsersId(users.getId());
        basketUser.setBooks(books);
        basketUser.setIsTheBasket(true);
        basketUser.setIsRequestCreated(false);
        basketUsersRepository.save(basketUser);
        return basketUser;
    }

    public String createRequestUser(String id) {
        BasketUser basketUser;
        try {
            basketUser = basketUsersRepository.findBasketUserByBasketUserId(Long.parseLong(id)).orElseThrow(() -> new UsernameNotFoundException("BasketUser not found"));
            basketUser.setIsRequestCreated(true);
            basketUser.setIsTheBasket(false);
            basketUsersRepository.save(basketUser);
            return "Ок";
        } catch (Throwable e) {
            e.printStackTrace();
            return "Ошибка";
        }
    }

    public String deleteBasketToUser(Long idBasketUser, Principal principal) {
        Users users = usersService.getUserByPrincipal(principal);
        BasketUser basketUser = basketUsersRepository.findBasketUserByBasketUserId(idBasketUser).orElseThrow(() -> new UsernameNotFoundException("BasketUser not found"));
        basketUsersRepository.delete(basketUser);
        return "delete";
    }


    public BasketUser issueBookToUser(Long id) {
        BasketUser basketUser = basketUsersRepository.findBasketUserByBasketUserId(id).orElseThrow(() -> new UsernameNotFoundException("BasketUser not found"));
        basketUser.setIsTheBasket(false);
        basketUser.setIsRequestCreated(false);
        basketUser.setIsIssued(true);
        basketUsersRepository.save(basketUser);
        return basketUser;
    }
    public String deleteBasketById(Long id) {
        BasketUser basketUser =  basketUsersRepository.findBasketUserByBasketUserId(id).orElseThrow(() -> new UsernameNotFoundException("BasketUser not found"));
        basketUsersRepository.delete(basketUser);
        return "Удалено";
    }
}
