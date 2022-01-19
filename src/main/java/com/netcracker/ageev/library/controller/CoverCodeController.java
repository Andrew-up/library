package com.netcracker.ageev.library.controller;

import com.netcracker.ageev.library.dto.AuthorsDTO;
import com.netcracker.ageev.library.dto.BooksDTO;
import com.netcracker.ageev.library.dto.CoverCodeDTO;
import com.netcracker.ageev.library.facade.CoverCodeFacade;
import com.netcracker.ageev.library.model.books.Authors;
import com.netcracker.ageev.library.model.books.CoverCode;
import com.netcracker.ageev.library.service.CoverCodeService;
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
@RequestMapping("/api/books/cover-code")
@CrossOrigin
public class CoverCodeController {

    private CoverCodeService coverCodeService;
    private CoverCodeFacade coverCodeFacade;
    private ResponseErrorValidator responseErrorValidator;

    @Autowired
    public CoverCodeController(CoverCodeService coverCodeService, CoverCodeFacade coverCodeFacade, ResponseErrorValidator responseErrorValidator) {
        this.coverCodeService = coverCodeService;
        this.coverCodeFacade = coverCodeFacade;
        this.responseErrorValidator = responseErrorValidator;
    }

    @GetMapping("/all")
    public ResponseEntity<List<CoverCodeDTO>> getAllCoverCode() {
        List<CoverCodeDTO> coverCodeDTOS = coverCodeService.getAllCoverCode()
                .stream()
                .map(coverCodeFacade::coverCodeDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(coverCodeDTOS, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createAuthor(@Valid @RequestBody CoverCodeDTO coverCodeDTO, BindingResult bindingResult, Principal principal) {
        ResponseEntity<Object> listError = responseErrorValidator.mappedValidatorService(bindingResult);
        if(!ObjectUtils.isEmpty(listError))return listError;
        CoverCode  coverCode = coverCodeService.createCoverCode(coverCodeDTO,principal);
        CoverCodeDTO coverCodeDTO1 = coverCodeFacade.coverCodeDTO(coverCode);
        return new ResponseEntity<>(coverCodeDTO1,HttpStatus.OK);
    }

}
