package com.netcracker.ageev.library.service;

import com.netcracker.ageev.library.dto.BooksDTO;
import com.netcracker.ageev.library.exception.DataNotFoundException;
import com.netcracker.ageev.library.model.books.Books;
import com.netcracker.ageev.library.model.users.BasketUser;
import com.netcracker.ageev.library.model.users.Users;
import com.netcracker.ageev.library.repository.books.BooksRepository;
import com.netcracker.ageev.library.repository.users.BasketUsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class BasketUsersService {
    public static final Logger LOG = LoggerFactory.getLogger(BasketUsersService.class);

    private final BasketUsersRepository basketUsersRepository;
    private final UsersService usersService;
    private final BooksRepository booksRepository;


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

    public List<BasketUser> getBasketByBookId(Long bookId, Long userId) {
        return this.basketUsersRepository.findAllByBooksIdAndUsersId(bookId, userId);
    }

    public List<BasketUser> getAllBasketById(Long id) {
        return this.basketUsersRepository.findAllByUsersId(id);
    }


    public List<BasketUser> getBasketToAllUsers(Long id) {
        List<BasketUser> basketUserList = new ArrayList<>();
        try {
            return this.basketUsersRepository.findBasketUserByUsersId(id);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error(String.valueOf(e));
            return basketUserList;
        }
    }


    public String cancelRequest(Long idRequest) {
        try {
            BasketUser basketUser = this.basketUsersRepository.findBasketUserByBasketUserId(idRequest).orElseThrow(() -> new DataNotFoundException("Books not found id:" + idRequest));
            basketUser.setIsRequestCreated(false);
            basketUser.setIsTheBasket(true);
            basketUsersRepository.save(basketUser);
            return "Успешная отмена";
        } catch (DataNotFoundException e) {
            e.printStackTrace();
            return "Ошибка";
        }

    }

    public List<BasketUser> getAllBasketToUser(Long id) {

        List<BasketUser> basketUserList = new ArrayList<>();
        try {
            return this.basketUsersRepository.findBasketUserByUsersIdAndIsTheBasketIsTrueAndIsRequestCreatedIsFalse(id);
        } catch (Exception e) {
            LOG.error("user id basket: " + id + " " + String.valueOf(e));
            return basketUserList;
        }

    }

    public BasketUser addBasketToUser(BooksDTO booksDTO, Principal principal) {
        Users users = usersService.getUserByPrincipal(principal);
        Books books = booksRepository.findBooksById(booksDTO.getBookId()).orElseThrow(() -> new DataNotFoundException("Books not found id: " + booksDTO.getBookId()));
        BasketUser basketUser = new BasketUser();
        basketUser.setPrice(books.getPrice());
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
            basketUser = basketUsersRepository.findBasketUserByBasketUserId(Long.parseLong(id)).orElseThrow(() -> new DataNotFoundException("BasketUser not found basket id: " + id));
            basketUser.setIsRequestCreated(true);
            basketUser.setIsTheBasket(false);
            basketUsersRepository.save(basketUser);
            return "Ок";
        } catch (DataNotFoundException e) {
            e.printStackTrace();
            return "Ошибка";
        }
    }

    public String deleteBasketToUser(Long idBasketUser, Principal principal) {
        BasketUser basketUser = basketUsersRepository.findBasketUserByBasketUserId(idBasketUser).orElseThrow(() -> new DataNotFoundException("BasketUser not found id delete:" + idBasketUser));
        basketUsersRepository.delete(basketUser);
        return "delete";
    }


    public BasketUser issueBookToUser(Long id) {
        BasketUser basketUser = basketUsersRepository.findBasketUserByBasketUserId(id).orElseThrow(() -> new DataNotFoundException("BasketUser not found. deleteBasketToUser " + id));
        basketUser.setIsTheBasket(false);
        basketUser.setIsRequestCreated(false);
        basketUser.setIsIssued(true);
        basketUsersRepository.save(basketUser);
        return basketUser;
    }

    public String deleteBasketById(Long id) {
        BasketUser basketUser = basketUsersRepository.findBasketUserByBasketUserId(id).orElseThrow(() -> new DataNotFoundException("BasketUser not found. deleteBasketById " + id));
        basketUsersRepository.delete(basketUser);
        return "Удалено";
    }
}
