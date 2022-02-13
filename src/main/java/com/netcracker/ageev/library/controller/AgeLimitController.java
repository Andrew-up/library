package com.netcracker.ageev.library.controller;

import com.netcracker.ageev.library.dto.AgeLimitDTO;
import com.netcracker.ageev.library.dto.PriceRentDTO;
import com.netcracker.ageev.library.facade.AgeLimitFacade;
import com.netcracker.ageev.library.model.books.AgeLimit;
import com.netcracker.ageev.library.service.AgeLimitService;
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
@RequestMapping("/api/books/age-limit")
@CrossOrigin
public class AgeLimitController {

    @Autowired
    private AgeLimitFacade limitFacade;
    @Autowired
    private ResponseErrorValidator responseErrorValidator;
    @Autowired
    private AgeLimitService ageLimitService;

    @GetMapping("/{id}")
    public ResponseEntity<Object> getIdLimitById(@PathVariable("id") String idLimit){
        AgeLimit ageLimit = ageLimitService.getAgeLimitById(Integer.parseInt(idLimit));
        AgeLimitDTO ageLimitDTO = limitFacade.ageLimitDTO(ageLimit);
        return new ResponseEntity<>(ageLimitDTO,HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createAgeLimit(@Valid @RequestBody AgeLimitDTO ageLimitDTO,
                                                 BindingResult bindingResult,Principal principal){
        ResponseEntity<Object> listError = responseErrorValidator.mappedValidatorService(bindingResult);
        if (!ObjectUtils.isEmpty(listError)) return  listError;
        AgeLimit ageLimit = ageLimitService.createAgeLimit(ageLimitDTO,principal);
        AgeLimitDTO ageLimitCreated = limitFacade.ageLimitDTO(ageLimit);
        return new ResponseEntity<>(ageLimitCreated,HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<AgeLimitDTO>> getAllAgeLimit(){
        List<AgeLimitDTO> ageLimitDTOS = ageLimitService.getAllAgeLimit()
                .stream()
                .map(limitFacade::ageLimitDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(ageLimitDTOS, HttpStatus.OK);
    }

}
