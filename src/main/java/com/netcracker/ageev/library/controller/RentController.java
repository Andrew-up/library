package com.netcracker.ageev.library.controller;

import com.netcracker.ageev.library.dto.AgeLimitDTO;
import com.netcracker.ageev.library.dto.PublisherDTO;
import com.netcracker.ageev.library.dto.RentDTO;
import com.netcracker.ageev.library.facade.RentFacade;
import com.netcracker.ageev.library.model.books.BookRent;
import com.netcracker.ageev.library.model.books.Publisher;
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
@RequestMapping("/api/books/rent")
@CrossOrigin
public class RentController {

    private RentFacade rentFacade;
    private ResponseErrorValidator responseErrorValidator;
    private RentService rentService;

    @Autowired
    public RentController(RentFacade rentFacade, ResponseErrorValidator responseErrorValidator, RentService rentService) {
        this.rentFacade = rentFacade;
        this.responseErrorValidator = responseErrorValidator;
        this.rentService = rentService;
    }


    @GetMapping("/all")
    public ResponseEntity<List<RentDTO>> getAllRent() {
        List<RentDTO> rentDTOS = rentService.getAllRent()
                .stream()
                .map(rentFacade::rentDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(rentDTOS, HttpStatus.OK);
    }

    @GetMapping("/myBooksRentAll")
    public ResponseEntity<List<RentDTO>> getAllRent(Principal principal) {
        List<RentDTO> rentDTOS = rentService.getAllRentByUserId(principal)
                .stream()
                .map(rentFacade::rentDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(rentDTOS, HttpStatus.OK);
    }


    @PostMapping("/create/{idBaskedToUser}")
    public ResponseEntity<Object> createRent(@Valid @RequestBody RentDTO rentDTO, BindingResult bindingResult, Principal principal, @PathVariable String idBaskedToUser) {
        ResponseEntity<Object> listError = responseErrorValidator.mappedValidatorService(bindingResult);
        if (!ObjectUtils.isEmpty(listError)) return listError;
        BookRent bookRent = rentService.createRent(rentDTO, principal, Long.parseLong(idBaskedToUser));
        RentDTO rentDTO1 = rentFacade.rentDTO(bookRent);
        return new ResponseEntity<>(rentDTO1, HttpStatus.OK);
    }

    @GetMapping("/delete/{idRent}")
    public ResponseEntity<Object> deleteSeries(@PathVariable("idRent") String idRent) {
        String resultDelete = rentService.deleteRent(idRent);
        return new ResponseEntity<>(new MessageResponse(resultDelete), HttpStatus.OK);
    }


}
