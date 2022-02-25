package com.netcracker.ageev.library.service;

import com.netcracker.ageev.library.dto.PublisherDTO;
import com.netcracker.ageev.library.dto.RentDTO;
import com.netcracker.ageev.library.model.books.BookRent;
import com.netcracker.ageev.library.model.users.Employee;
import com.netcracker.ageev.library.repository.books.BookRentRepository;
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

    @Autowired
    public RentService(BookRentRepository bookRentRepository,
                       BooksService booksService,
                       EmployeeService employeeService,
                       PriceService priceService,
                       UsersService usersService) {

        this.bookRentRepository = bookRentRepository;
        this.booksService = booksService;
        this.employeeService = employeeService;
        this.priceService = priceService;
        this.usersService = usersService;
    }

    public List<BookRent> getAllRent() {
        return bookRentRepository.findAllByOrderByIdAsc();
    }

    public BookRent createRent(RentDTO rentDTO, Principal principal) {
        BookRent bookRent = new BookRent();
        Employee employee = new Employee();
        ArrayList<String> arrayListError = isRentCorrect(rentDTO);

        bookRent.setBooksId(booksService.getBookById(rentDTO.getBookId()));
        bookRent.setDateIssue(rentDTO.getDateIssue());
        bookRent.setDateReturn(rentDTO.getDateReturn());
        employee = employeeService.getEmployeeById(rentDTO.getEmployeeId());
        if (employee.getId()!=null){
            bookRent.setEmployeeId(employee);
        }
        else {
            arrayListError.add("Пользователь: "+usersService.getUserById(rentDTO.getUserId()).getEmail().toString() +" не записан как работник");
        }
        bookRent.setPriceId(priceService.getPriceById(rentDTO.getPriceId()));
        if (rentDTO.getUserId() != null) {
            bookRent.setUsersId(usersService.getUserById(rentDTO.getUserId()));
        }

        if (!ObjectUtils.isEmpty(arrayListError)) {
            bookRent.setDateIssue(arrayListError.toString());
            return bookRent;
        }

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
