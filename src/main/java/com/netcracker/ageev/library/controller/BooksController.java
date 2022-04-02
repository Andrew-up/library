package com.netcracker.ageev.library.controller;

import com.netcracker.ageev.library.dto.BooksDTO;
import com.netcracker.ageev.library.dto.SearchDTO;
import com.netcracker.ageev.library.facade.BooksFacade;
import com.netcracker.ageev.library.model.books.Books;
import com.netcracker.ageev.library.service.BasketUsersService;
import com.netcracker.ageev.library.service.BooksService;
import com.netcracker.ageev.library.validators.ResponseErrorValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
@CrossOrigin

public class BooksController {
    private BooksService booksService;
    private BooksFacade booksFacade;
    private ResponseErrorValidator responseErrorValidator;
    private final BasketUsersService basketUsersService;

    @Autowired
    public BooksController(BooksService booksService,
                           BooksFacade booksFacade,
                           ResponseErrorValidator responseErrorValidator,
                           BasketUsersService basketUsersService) {
        this.booksService = booksService;
        this.booksFacade = booksFacade;
        this.responseErrorValidator = responseErrorValidator;
        this.basketUsersService = basketUsersService;
    }

    @PermitAll()
    @GetMapping("/all")
    public ResponseEntity<List<BooksDTO>> getAllBooks() {
        List<BooksDTO> booksDTOS = booksService.getAllBooks()
                .stream()
                .map(booksFacade::booksDTO)
                .collect(Collectors.toList());
        System.out.println("размер: "+booksDTOS.size());
        System.out.println(booksDTOS);
        return new ResponseEntity<>(booksDTOS, HttpStatus.OK);
    }

    @PermitAll()
    @PostMapping("/search")
    public ResponseEntity<List<BooksDTO>> getAllBooksByInputSearch(@Valid @RequestBody SearchDTO search) {

        String inputSearch = search.getSearch().toLowerCase();
        String typeSearch = search.getTypeSearch().toLowerCase();
        System.out.println("Строка поиска:" + inputSearch);
        List<BooksDTO> booksDTOS = booksService.getAllBooksByInputSearch(inputSearch,typeSearch)
                .stream()
                .map(booksFacade::searchBooksDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(booksDTOS, HttpStatus.OK);
    }


    @PermitAll()
    @GetMapping("/AllBookByPage")
    public ResponseEntity<List<BooksDTO>> getAllBooksByNumberPage(@RequestParam(required = false) String title,
                                                                  @RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "3") int size) {
        Pageable paging = PageRequest.of(page, size);
        Page<Books> booksPage;
        booksPage = booksService.getAllBooksByNumberPage(paging);
        List<BooksDTO> booksDTO = booksPage
                .stream()
                .map(booksFacade::booksDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(booksDTO, HttpStatus.OK);
    }


    @PostMapping("/create")
    public ResponseEntity<Object> createBook(@Valid @RequestBody BooksDTO booksDTO,
                                             BindingResult bindingResult, Principal principal) {
        ResponseEntity<Object> listError = responseErrorValidator.mappedValidatorService(bindingResult);
        if (!ObjectUtils.isEmpty(listError)) return listError;
        Books books = booksService.createBook(booksDTO, principal);
        BooksDTO booksDTO1 = new BooksDTO();
        if(books.getId()!=null){
            booksDTO1 = booksFacade.booksDTO(books);
        }
        else {
            booksDTO1.setBookTitle(books.getBookTitle());
        }
        return new ResponseEntity<>(booksDTO1, HttpStatus.OK);
    }

    @GetMapping("/getAllById/{id}")
    public ResponseEntity<List<BooksDTO>> getRentalRequestToUsersId(@PathVariable String id, Principal principal) {
        System.out.println("TTTTTTTTTTTTTTTT");
        List<BooksDTO> booksDTOS = basketUsersService.getBasketToAllUsers(Long.parseLong(id))
                .stream()
                .map(booksFacade::booksDTO2)
                .collect(Collectors.toList());
        return new ResponseEntity<>(booksDTOS, HttpStatus.OK);
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<BooksDTO> getBookById(@PathVariable String id, Principal principal) {
        Books books = booksService.getBookUserById(Long.parseLong(id));
        BooksDTO booksDTO = null;
        try {
            booksDTO = booksFacade.booksDTO(books);
        } catch (Exception e) {
            booksDTO = new BooksDTO();
            booksDTO.setBookTitle("Не найдено");
            e.printStackTrace();
        }
        return new ResponseEntity<>(booksDTO, HttpStatus.OK);
    }


}
