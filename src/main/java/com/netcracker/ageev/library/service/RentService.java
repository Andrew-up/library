package com.netcracker.ageev.library.service;

import com.netcracker.ageev.library.dto.RentDTO;
import com.netcracker.ageev.library.exception.DataNotFoundException;
import com.netcracker.ageev.library.model.books.BookRent;
import com.netcracker.ageev.library.model.books.Books;
import com.netcracker.ageev.library.model.users.BasketUser;
import com.netcracker.ageev.library.model.users.Employee;
import com.netcracker.ageev.library.model.users.Users;
import com.netcracker.ageev.library.repository.books.BookRentRepository;
import com.netcracker.ageev.library.repository.books.BooksRepository;
import com.netcracker.ageev.library.repository.users.BasketUsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;


@Service
public class RentService {

    public static final Logger LOG = LoggerFactory.getLogger(RentService.class);


    private final BookRentRepository bookRentRepository;
    private final BooksService booksService;
    private final EmployeeService employeeService;
    private final PriceService priceService;
    private final UsersService usersService;
    private final BasketUsersService basketUsersService;
    private final BooksRepository booksRepository;
    private final BasketUsersRepository basketUsersRepository;

    @Autowired
    public RentService(BookRentRepository bookRentRepository,
                       BooksService booksService,
                       EmployeeService employeeService,
                       PriceService priceService,
                       UsersService usersService,
                       BasketUsersService basketUsersService,
                       BooksRepository booksRepository,
                       BasketUsersRepository basketUsersRepository) {


        this.bookRentRepository = bookRentRepository;
        this.booksService = booksService;
        this.employeeService = employeeService;
        this.priceService = priceService;
        this.usersService = usersService;
        this.basketUsersService = basketUsersService;
        this.booksRepository = booksRepository;
        this.basketUsersRepository = basketUsersRepository;
    }

    public List<BookRent> getAllRent() {
        return bookRentRepository.findAllByOrderByIdAsc();
    }

    public List<BookRent> getAllRentByUserId(Principal principal) {
        Users user = usersService.getUserByPrincipal(principal);
        List<BookRent> bookRent = bookRentRepository.findBookRentByUsersId(user);
        return bookRent;
    }

    public String deleteRent(String idRent) {
        BookRent bookRent = bookRentRepository.findBookRentById(Integer.parseInt(idRent)).orElseThrow(() -> new DataNotFoundException("not found. deleteRent: "+idRent));
        Books books = booksService.getBookById(bookRent.getBooksId().getId());
        books.setCountBooks(books.getCountBooks()+1);
        booksRepository.save(books);
        bookRentRepository.delete(bookRent);
        basketUsersService.deleteBasketById(bookRent.getBasketUser().getBasketUserId());
        return "Удалено";
    }

    public BookRent createRentByBaskedId(RentDTO rentDTO, Principal principal, Long baskedId) {
        BookRent bookRent = new BookRent();
        Employee employee = new Employee();
        BasketUser basketUser = basketUsersService.issueBookToUser(baskedId);
        ArrayList<String> arrayListError = isRentCorrect(rentDTO);
        bookRent.setBooksId(booksService.getBookById(rentDTO.getBookId()));
        bookRent.setDateIssue(rentDTO.getDateIssue());
        bookRent.setDateReturn(rentDTO.getDateReturn());
        bookRent.setBasketUser(basketUser);
        employee = employeeService.getEmployeeById(rentDTO.getEmployeeId());
        Books books = booksService.getBookById(rentDTO.getBookId());
        books.setCountBooks(books.getCountBooks() - 1);
        if (employee.getId() != null) {
            bookRent.setEmployeeId(employee);
        } else {
            LOG.info("User not employee: "+employee.getUsers().getEmail());
            arrayListError.add("Пользователь: " + employee.getUsers().getEmail() + " не записан как работник");
        }
        bookRent.setPriceId(priceService.getPriceById(rentDTO.getPriceId()));
        if (rentDTO.getUserId() != null) {
            bookRent.setUsersId(usersService.getUserById(rentDTO.getUserId()));
        }
        if (!ObjectUtils.isEmpty(arrayListError)) {
            bookRent.setDateIssue(arrayListError.toString());
            return bookRent;
        }
        basketUser.setBookRentDateIssue(bookRent.getDateIssue());
        basketUsersRepository.save(basketUser);
        booksRepository.save(books);
        return bookRentRepository.save(bookRent);
    }
    public BookRent createRent(RentDTO rentDTO) {
        BookRent bookRent = new BookRent();
        BasketUser basketUser = new BasketUser();
        basketUser.setIsIssued(true);
        basketUser.setBookRentDateIssue(rentDTO.getDateIssue());
        basketUser.setPrice(booksService.getBookById(rentDTO.getBookId()).getPrice());
        basketUser.setUsersId(rentDTO.getUserId());
        basketUser.setIsRequestCreated(false);
        basketUser.setIsTheBasket(false);
        basketUser.setBooks(booksService.getBookById(rentDTO.getBookId()));
        bookRent.setBooksId(booksService.getBookById(rentDTO.getBookId()));
        bookRent.setDateIssue(rentDTO.getDateIssue());
        bookRent.setDateReturn(rentDTO.getDateReturn());
        bookRent.setBasketUser(basketUser);
        bookRent.setUsersId(usersService.getUserById(rentDTO.getUserId()));
        bookRent.setEmployeeId(employeeService.getEmployeeById(rentDTO.getEmployeeId()));
        bookRent.setPriceId(priceService.getPriceById(rentDTO.getPriceId()));
        basketUsersRepository.save(basketUser);
        return bookRentRepository.save(bookRent);
    }

    private ArrayList<String> isRentCorrect(RentDTO rentDTO) {
        ArrayList<String> listError = new ArrayList<>();
        if (rentDTO.getBookId() == null) {
            listError.add("id книги не корректно");
        }
        if (rentDTO.getEmployeeId() == null) {
            listError.add("Id работника не корректно");
        }
        if (rentDTO.getUserId() == null) {
            listError.add("Id арендатора не корректно");
        }
        if (rentDTO.getDateIssue() == null) {
            listError.add("Дата выдачи  не корректно");
        }
        if (rentDTO.getPriceId() == null) {
            listError.add("Id оплаты не корректно");
        }

        return listError;
    }

}
