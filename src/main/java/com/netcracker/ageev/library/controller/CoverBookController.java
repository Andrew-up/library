package com.netcracker.ageev.library.controller;

import com.netcracker.ageev.library.dto.CoverBookDTO;
import com.netcracker.ageev.library.facade.CoverBookFacade;
import com.netcracker.ageev.library.model.books.CoverBook;
import com.netcracker.ageev.library.payload.responce.MessageResponse;
import com.netcracker.ageev.library.service.CoverBookService;
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
public class CoverBookController {

    private final CoverBookService coverBookService;
    private final CoverBookFacade coverBookFacade;
    private final ResponseErrorValidator responseErrorValidator;

    @Autowired
    public CoverBookController(CoverBookService coverBookService,
                               CoverBookFacade coverBookFacade,
                               ResponseErrorValidator responseErrorValidator) {
        this.coverBookService = coverBookService;
        this.coverBookFacade = coverBookFacade;
        this.responseErrorValidator = responseErrorValidator;
    }

    @GetMapping("/books/cover-code/all")
    public ResponseEntity<List<CoverBookDTO>> getAllCoverBook() {
        List<CoverBookDTO> coverBookDTOS = coverBookService.getAllCoverBook()
                .stream()
                .map(coverBookFacade::coverBookDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(coverBookDTOS, HttpStatus.OK);
    }

    @PostMapping("/staff/books/cover-code/create")
    public ResponseEntity<Object> createCoverBook(@Valid @RequestBody CoverBookDTO coverBookDTO, BindingResult bindingResult, Principal principal) {
        ResponseEntity<Object> listError = responseErrorValidator.mappedValidatorService(bindingResult);
        if(!ObjectUtils.isEmpty(listError))return listError;
        CoverBook coverBook = coverBookService.createCoverBook(coverBookDTO,principal);
        CoverBookDTO coverBookDTO1 = coverBookFacade.coverBookDTO(coverBook);
        return new ResponseEntity<>(coverBookDTO1,HttpStatus.OK);
    }

    @PostMapping("/staff/books/cover-code/update")
    public ResponseEntity<Object> updateCoverBook(@Valid @RequestBody CoverBookDTO coverBookDTO, BindingResult bindingResult, Principal principal){
        ResponseEntity<Object> listError = responseErrorValidator.mappedValidatorService(bindingResult);
        if (!ObjectUtils.isEmpty(listError)) return listError;
        CoverBook coverBook = coverBookService.updateCoverBook(coverBookDTO,principal);
        CoverBookDTO coverBookDTO1 = coverBookFacade.coverBookDTO(coverBook);
        return new ResponseEntity<>(coverBookDTO1,HttpStatus.OK);
    }

    @PostMapping("/staff/books/cover-code/delete")
    public ResponseEntity<Object> deleteCoverBook(@Valid @RequestBody String id, BindingResult bindingResult, Principal principal){
        String resultDelete = coverBookService.deleteGenre(Integer.parseInt(id),principal);
        return new ResponseEntity<>(new MessageResponse(resultDelete),HttpStatus.OK);
    }


}
