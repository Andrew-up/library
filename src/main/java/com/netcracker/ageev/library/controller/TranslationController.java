package com.netcracker.ageev.library.controller;

import com.netcracker.ageev.library.dto.AgeLimitDTO;
import com.netcracker.ageev.library.dto.TranslationDTO;
import com.netcracker.ageev.library.facade.TranslationFacade;
import com.netcracker.ageev.library.model.books.AgeLimit;
import com.netcracker.ageev.library.model.books.TranslationBooks;
import com.netcracker.ageev.library.payload.responce.MessageResponse;
import com.netcracker.ageev.library.service.TranslationService;
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
@RequestMapping("/api/books/translation")
@CrossOrigin
public class TranslationController {

    private final TranslationFacade translationFacade;
    private final TranslationService translationService;
    private final ResponseErrorValidator responseErrorValidator;

    @Autowired
    public TranslationController(TranslationFacade translationFacade, TranslationService translationService, ResponseErrorValidator responseErrorValidator) {
        this.translationFacade = translationFacade;
        this.translationService = translationService;
        this.responseErrorValidator = responseErrorValidator;
    }

    @GetMapping("/all")
    public ResponseEntity<List<TranslationDTO>> getAllTranslation(){
        List<TranslationDTO>translationDTOS = translationService.getAllTranslation()
                .stream()
                .map(translationFacade::translationDTO)
                .collect(Collectors.toList());
        return  new ResponseEntity<>(translationDTOS, HttpStatus.OK);
    }
    @PostMapping("/create")
    public ResponseEntity<Object> createTranslation(@Valid @RequestBody TranslationDTO translationDTO, BindingResult bindingResult, Principal principal) {
        ResponseEntity<Object> listError = responseErrorValidator.mappedValidatorService(bindingResult);
        if(!ObjectUtils.isEmpty(listError))return listError;
        TranslationBooks translationBooks = translationService.createTranslation(translationDTO,principal);
        TranslationDTO translationDTO1 = translationFacade.translationDTO(translationBooks);
        return new ResponseEntity<>(translationDTO1,HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<Object> updateTranslation(@Valid @RequestBody TranslationDTO translationDTO, BindingResult bindingResult, Principal principal){
        ResponseEntity<Object> listError = responseErrorValidator.mappedValidatorService(bindingResult);
        if (!ObjectUtils.isEmpty(listError)) return listError;
        TranslationBooks translationBooks = translationService.updateTranslationBooks(translationDTO,principal);
        TranslationDTO translationDTO1 = translationFacade.translationDTO(translationBooks);
        return new ResponseEntity<>(translationDTO1,HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity<Object> deleteTranslation(@Valid @RequestBody String id, BindingResult bindingResult, Principal principal){
        String resultDelete = translationService.deleteTranslation(Integer.parseInt(id),principal);
        return new ResponseEntity<>(new MessageResponse(resultDelete),HttpStatus.OK);
    }


}
