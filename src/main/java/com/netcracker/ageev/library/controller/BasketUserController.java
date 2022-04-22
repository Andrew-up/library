package com.netcracker.ageev.library.controller;

import com.netcracker.ageev.library.dto.BasketUsersDTO;
import com.netcracker.ageev.library.dto.BooksDTO;
import com.netcracker.ageev.library.facade.BasketFacade;
import com.netcracker.ageev.library.model.users.BasketUser;
import com.netcracker.ageev.library.model.users.Users;
import com.netcracker.ageev.library.payload.responce.MessageResponse;
import com.netcracker.ageev.library.service.BasketUsersService;
import com.netcracker.ageev.library.service.UsersService;
import com.netcracker.ageev.library.validators.ResponseErrorValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class BasketUserController {
    private static final Logger LOG = LoggerFactory.getLogger(BasketUserController.class);

    private final BasketUsersService basketUsersService;
    private final BasketFacade basketFacade;
    private final ResponseErrorValidator errorValidator;
    private final UsersService usersService;

    @Autowired
    public BasketUserController(BasketUsersService basketUsersService,
                                BasketFacade basketFacade,
                                ResponseErrorValidator errorValidator,
                                UsersService usersService) {
        this.basketUsersService = basketUsersService;
        this.basketFacade = basketFacade;
        this.errorValidator = errorValidator;
        this.usersService = usersService;
    }

    @GetMapping("/users/basket/all")
    public ResponseEntity<List<BasketUsersDTO>> getAllPrice() {
        List<BasketUsersDTO> basketUsersDTOS = basketUsersService.getAllBasket()
                .stream()
                .map(basketFacade::basketUsersDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(basketUsersDTOS, HttpStatus.OK);
    }

    @GetMapping("/users/basket/{bookId}")
    public ResponseEntity<List<BasketUsersDTO>> getBasketByBookId(@PathVariable String bookId,Principal principal) {
        Users user = usersService.getCurrentUser(principal);
        List<BasketUsersDTO> basketUsersDTOS = basketUsersService.getBasketByBookId(Long.parseLong(bookId),user.getId())
                .stream()
                .map(basketFacade::basketUsersDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(basketUsersDTOS, HttpStatus.OK);
    }

    @GetMapping("/users/basket")
    public ResponseEntity<List<BasketUsersDTO>> getAllByUser(Principal principal) {
        Users users = usersService.getUserByPrincipal(principal);
        List<BasketUser> basketUsers = basketUsersService.getAllBasketToUser(users.getId());
        List<BasketUsersDTO> basketUsersDTOS;
        if (!ObjectUtils.isEmpty(basketUsers)){
            basketUsersDTOS = basketUsers.stream()
                   .map(basketFacade::basketUsersDTO)
                   .collect(Collectors.toList());
        }
        else {
            LOG.debug("Basket the user: "+users.getEmail()+" is empty");
            basketUsersDTOS = new ArrayList<>();
        }
        return new ResponseEntity<>(new ArrayList<>(basketUsersDTOS), HttpStatus.OK);
    }

    @GetMapping("/users/basket/all/{id}")
    public ResponseEntity<List<BasketUsersDTO>> getAllByUser(@PathVariable String id, Principal principal) {
        List<BasketUsersDTO> basketUsersDTOS;
        basketUsersDTOS = basketUsersService.getBasketToAllUsers(Long.parseLong(id))
                .stream()
                .map(basketFacade::basketUsersDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(basketUsersDTOS, HttpStatus.OK);
    }

    @GetMapping("/users/basket/cancel/{idRequest}")
    public ResponseEntity<Object> cancelRequestByUser(@PathVariable String idRequest, Principal principal) {
        String result = basketUsersService.cancelRequest(Long.parseLong(idRequest));
        return new ResponseEntity<>(new MessageResponse(result), HttpStatus.OK);
    }

    @PostMapping("/users/basket/addBasketToUser")
    public ResponseEntity<Object> createBasketToUser(@Valid @RequestBody BooksDTO booksDTO,
                                                     BindingResult bindingResult, Principal principal) {
        ResponseEntity<Object> listError = errorValidator.mappedValidatorService(bindingResult);
        if (!ObjectUtils.isEmpty(listError)) return listError;
        BasketUser basketUser = basketUsersService.addBasketToUser(booksDTO, principal);
        return new ResponseEntity<>(new MessageResponse(basketUser.getBooks().getId().toString()), HttpStatus.OK);
    }

    @PostMapping("/users/basket/delete")
    public ResponseEntity<Object> deleteBasketToUser(@Valid @RequestBody String id,
                                                     BindingResult bindingResult, Principal principal) {
        ResponseEntity<Object> listError = errorValidator.mappedValidatorService(bindingResult);
        if (!ObjectUtils.isEmpty(listError)) return listError;
        String deleteResult = basketUsersService.deleteBasketToUser(Long.parseLong(id), principal);
        return new ResponseEntity<>(new MessageResponse(deleteResult), HttpStatus.OK);
    }

    @PostMapping("/users/basket/createBookRequest")
    public ResponseEntity<Object> createRequestUser(@Valid @RequestBody String id,
                                                    BindingResult bindingResult) {
        ResponseEntity<Object> listError = errorValidator.mappedValidatorService(bindingResult);
        if (!ObjectUtils.isEmpty(listError)) return listError;
        String result = basketUsersService.createRequestUser(id);
        return new ResponseEntity<>(new MessageResponse(result), HttpStatus.OK);
    }

}
