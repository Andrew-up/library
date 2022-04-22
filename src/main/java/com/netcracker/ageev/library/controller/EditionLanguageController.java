package com.netcracker.ageev.library.controller;

import com.netcracker.ageev.library.dto.EditionLanguageDTO;
import com.netcracker.ageev.library.facade.EditionLanguageFacade;
import com.netcracker.ageev.library.model.books.EditionLanguage;
import com.netcracker.ageev.library.payload.responce.MessageResponse;
import com.netcracker.ageev.library.service.EditionLanguageService;
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
public class EditionLanguageController {
    private final EditionLanguageService editionLanguageService;
    private final EditionLanguageFacade editionLanguageFacade;
    private final ResponseErrorValidator responseErrorValidator;

    @Autowired
    public EditionLanguageController(EditionLanguageService editionLanguageService,
                                     EditionLanguageFacade editionLanguageFacade,
                                     ResponseErrorValidator responseErrorValidator) {
        this.editionLanguageService = editionLanguageService;
        this.editionLanguageFacade = editionLanguageFacade;
        this.responseErrorValidator = responseErrorValidator;
    }


    @GetMapping("/books/edition-language/all")
    ResponseEntity<List<EditionLanguageDTO>> getAllLanguage(){
        List<EditionLanguageDTO> editionLanguageDTOS = editionLanguageService.getAllLanguage()
                .stream()
                .map(editionLanguageFacade::editionLanguageDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(editionLanguageDTOS, HttpStatus.OK);
    }

    @PostMapping("/staff/books/edition-language/create")
    public ResponseEntity<Object> createEditionLanguage(@Valid @RequestBody EditionLanguageDTO editionLanguageDTO, BindingResult bindingResult, Principal principal) {
        ResponseEntity<Object> listError = responseErrorValidator.mappedValidatorService(bindingResult);
        if(!ObjectUtils.isEmpty(listError)) return listError;
        EditionLanguage editionLanguage = editionLanguageService.createEditionLanguage(editionLanguageDTO,principal);
        EditionLanguageDTO editionLanguageDTO1 = editionLanguageFacade.editionLanguageDTO(editionLanguage);
        return new ResponseEntity<>(editionLanguageDTO1,HttpStatus.OK);
    }


    @PostMapping("/staff/books/edition-language/update")
    public ResponseEntity<Object> updateEditionLanguage(@Valid @RequestBody EditionLanguageDTO editionLanguageDTO, BindingResult bindingResult, Principal principal){
        ResponseEntity<Object> listError = responseErrorValidator.mappedValidatorService(bindingResult);
        if (!ObjectUtils.isEmpty(listError)) return listError;
        EditionLanguage editionLanguage = editionLanguageService.updateEditionLanguage(editionLanguageDTO,principal);
        EditionLanguageDTO editionLanguageDTO1 = editionLanguageFacade.editionLanguageDTO(editionLanguage);
        return new ResponseEntity<>(editionLanguageDTO1,HttpStatus.OK);
    }

    @PostMapping("/staff/books/edition-language/delete")
    public ResponseEntity<Object> deleteTranslation(@Valid @RequestBody String id, BindingResult bindingResult, Principal principal){
        String resultDelete = editionLanguageService.deleteEditionLanguage(Integer.parseInt(id),principal);
        return new ResponseEntity<>(new MessageResponse(resultDelete),HttpStatus.OK);
    }
}
