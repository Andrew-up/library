package com.netcracker.ageev.library.controller;

import com.netcracker.ageev.library.dto.RentDTO;
import com.netcracker.ageev.library.facade.RentFacade;
import com.netcracker.ageev.library.model.books.BookRent;
import com.netcracker.ageev.library.payload.responce.MessageResponse;
import com.netcracker.ageev.library.service.RentService;
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
@RequestMapping("/api")
@CrossOrigin
public class RentController {

    private final RentFacade rentFacade;
    private final ResponseErrorValidator responseErrorValidator;
    private final RentService rentService;

    @Autowired
    public RentController(RentFacade rentFacade,
                          ResponseErrorValidator responseErrorValidator,
                          RentService rentService) {
        this.rentFacade = rentFacade;
        this.responseErrorValidator = responseErrorValidator;
        this.rentService = rentService;
    }


    @GetMapping("/staff/books/rent/all")
    public ResponseEntity<List<RentDTO>> getAllRent() {
        List<RentDTO> rentDTOS = rentService.getAllRent()
                .stream()
                .map(rentFacade::rentDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(rentDTOS, HttpStatus.OK);
    }

    @GetMapping("/books/rent/myBooksRentAll")
    public ResponseEntity<List<RentDTO>> getAllRent(Principal principal) {
        List<RentDTO> rentDTOS = rentService.getAllRentByUserId(principal)
                .stream()
                .map(rentFacade::rentDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(rentDTOS, HttpStatus.OK);
    }


    @PostMapping("/books/rent/create/{idBaskedToUser}")
    public ResponseEntity<Object> createRentToBasked(@Valid @RequestBody RentDTO rentDTO,
                                                     BindingResult bindingResult,
                                                     Principal principal,
                                                     @PathVariable String idBaskedToUser) {
        ResponseEntity<Object> listError = responseErrorValidator.mappedValidatorService(bindingResult);
        if (!ObjectUtils.isEmpty(listError)) return listError;
        BookRent bookRent = rentService.createRentByBaskedId(rentDTO, principal, Long.parseLong(idBaskedToUser));
        RentDTO rentDTO1 = rentFacade.rentDTO(bookRent);
        return new ResponseEntity<>(rentDTO1, HttpStatus.OK);
    }

    @PostMapping("/books/rent/create")
    public ResponseEntity<Object> createRent(@Valid @RequestBody RentDTO rentDTO,
                                             BindingResult bindingResult) {
        ResponseEntity<Object> listError = responseErrorValidator.mappedValidatorService(bindingResult);
        if (!ObjectUtils.isEmpty(listError)) return listError;
        BookRent bookRent = rentService.createRent(rentDTO);
        RentDTO rentDTO1 = rentFacade.rentDTO(bookRent);
        return new ResponseEntity<>(rentDTO1, HttpStatus.OK);
    }

    @GetMapping("/books/rent/delete/{idRent}")
    public ResponseEntity<Object> deleteSeries(@PathVariable("idRent") String idRent) {
        String resultDelete = rentService.deleteRent(idRent);
        return new ResponseEntity<>(new MessageResponse(resultDelete), HttpStatus.OK);
    }


}
