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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users/basket")
@CrossOrigin
public class BasketUserController {
    private BasketUsersService basketUsersService;
    private BasketFacade basketFacade;
    private ResponseErrorValidator errorValidator;
    private UsersService usersService;

    @Autowired
    public BasketUserController(BasketUsersService basketUsersService, BasketFacade basketFacade, ResponseErrorValidator errorValidator, UsersService usersService) {
        this.basketUsersService = basketUsersService;
        this.basketFacade = basketFacade;
        this.errorValidator = errorValidator;
        this.usersService = usersService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<BasketUsersDTO>> getAllPrice() {
        List<BasketUsersDTO> basketUsersDTOS = basketUsersService.getAllBasket()
                .stream()
                .map(basketFacade::basketUsersDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(basketUsersDTOS, HttpStatus.OK);
    }
    @GetMapping("/getBooksRentRequestAll")
    public ResponseEntity<List<BasketUsersDTO>> getBooksRentRequestAll() {
        List<BasketUsersDTO> basketUsersDTOS = basketUsersService.getAllBasket()
                .stream()
                .map(basketFacade::basketUsersDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(basketUsersDTOS, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<BasketUsersDTO>> getAllByUser(Principal principal) {
        Users users = usersService.getUserByPrincipal(principal);
        List<BasketUsersDTO> basketUsersDTOS = basketUsersService.getAllBasketToUser(users.getId())
                .stream()
                .map(basketFacade::basketUsersDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(basketUsersDTOS, HttpStatus.OK);
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<List<BasketUsersDTO>> getAllByUser(@PathVariable String id, Principal principal) {
        List<BasketUsersDTO> basketUsersDTOS = basketUsersService.getBasketToAllUsers(Long.parseLong(id))
                .stream()
                .map(basketFacade::basketUsersDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(basketUsersDTOS, HttpStatus.OK);
    }

    @GetMapping("/cancel/{idRequest}")
    public ResponseEntity<Object> cancelRequestByUser(@PathVariable String idRequest, Principal principal) {
        String result = basketUsersService.cancelRequest(Long.parseLong(idRequest));
        return new ResponseEntity<>(new MessageResponse(result), HttpStatus.OK);
    }

    @PostMapping("/addBasketToUser")
    public ResponseEntity<Object> createBasketToUser(@Valid @RequestBody BooksDTO booksDTO,
                                              BindingResult bindingResult, Principal principal){
        ResponseEntity<Object> listError = errorValidator.mappedValidatorService(bindingResult);
        if (!ObjectUtils.isEmpty(listError)) return  listError;
        BasketUser basketUser = basketUsersService.addBasketToUser(booksDTO,principal);
        System.out.println("test231:"+basketUser.getBooks().getId());
        return new ResponseEntity<>( new MessageResponse(basketUser.getBooks().getId().toString()),HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity<Object> deleteBasketToUser(@Valid @RequestBody String id,
                                                     BindingResult bindingResult, Principal principal){
        ResponseEntity<Object> listError = errorValidator.mappedValidatorService(bindingResult);
        if (!ObjectUtils.isEmpty(listError)) return  listError;
        String deleteResult = basketUsersService.deleteBasketToUser(Long.parseLong(id),principal);
        return new ResponseEntity<>( new MessageResponse(deleteResult),HttpStatus.OK);
    }

    @PostMapping("/createBookRequest")
    public ResponseEntity<Object> createRequestUser(@Valid @RequestBody String id,
                                                     BindingResult bindingResult){
        ResponseEntity<Object> listError = errorValidator.mappedValidatorService(bindingResult);
        if (!ObjectUtils.isEmpty(listError)) return  listError;
        String result = basketUsersService.createRequestUser(id);
        return new ResponseEntity<>(new MessageResponse(result),HttpStatus.OK);
    }

}
