package com.netcracker.ageev.library.controller;

import com.netcracker.ageev.library.dto.AgeLimitDTO;
import com.netcracker.ageev.library.facade.AgeLimitFacade;
import com.netcracker.ageev.library.model.books.AgeLimit;
import com.netcracker.ageev.library.payload.responce.MessageResponse;
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
@RequestMapping("/api/")
@CrossOrigin
public class AgeLimitController {

    private final AgeLimitFacade limitFacade;
    private final ResponseErrorValidator responseErrorValidator;
    private final AgeLimitService ageLimitService;

    @Autowired
    public AgeLimitController(AgeLimitFacade limitFacade,
                              ResponseErrorValidator responseErrorValidator,
                              AgeLimitService ageLimitService) {
        this.limitFacade = limitFacade;
        this.responseErrorValidator = responseErrorValidator;
        this.ageLimitService = ageLimitService;
    }

    @GetMapping("books/age-limit/{id}")
    public ResponseEntity<Object> getIdLimitById(@PathVariable("id") String idLimit) {
        AgeLimit ageLimit = ageLimitService.getAgeLimitById(Integer.parseInt(idLimit));
        AgeLimitDTO ageLimitDTO = limitFacade.ageLimitDTO(ageLimit);
        return new ResponseEntity<>(ageLimitDTO, HttpStatus.OK);
    }

    @PostMapping("staff/books/age-limit/create")
    public ResponseEntity<Object> createAgeLimit(@Valid @RequestBody AgeLimitDTO ageLimitDTO,
                                                 BindingResult bindingResult, Principal principal) {
        ResponseEntity<Object> listError = responseErrorValidator.mappedValidatorService(bindingResult);
        if (!ObjectUtils.isEmpty(listError)) return listError;
        AgeLimit ageLimit = ageLimitService.createAgeLimit(ageLimitDTO, principal);
        AgeLimitDTO ageLimitCreated = limitFacade.ageLimitDTO(ageLimit);
        return new ResponseEntity<>(ageLimitCreated, HttpStatus.OK);
    }

    @GetMapping("books/age-limit/all")
    public ResponseEntity<List<AgeLimitDTO>> getAllAgeLimit() {
        List<AgeLimitDTO> ageLimitDTOS = ageLimitService.getAllAgeLimit()
                .stream()
                .map(limitFacade::ageLimitDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(ageLimitDTOS, HttpStatus.OK);
    }

    @PostMapping("staff/books/age-limit/update")
    public ResponseEntity<Object> updateAgeLimit(@Valid @RequestBody AgeLimitDTO ageLimitDTO, BindingResult bindingResult, Principal principal) {
        ResponseEntity<Object> listError = responseErrorValidator.mappedValidatorService(bindingResult);
        if (!ObjectUtils.isEmpty(listError)) return listError;
        AgeLimit ageLimit = ageLimitService.updateAgeLimit(ageLimitDTO, principal);
        AgeLimitDTO ageLimitDTO1 = limitFacade.ageLimitDTO(ageLimit);
        return new ResponseEntity<>(ageLimitDTO1, HttpStatus.OK);
    }

    @PostMapping("staff/books/age-limit/delete")
    public ResponseEntity<Object> deleteAgeLimit(@Valid @RequestBody String id, BindingResult bindingResult, Principal principal) {
        String resultDelete = ageLimitService.deleteGenre(Integer.parseInt(id), principal);
        return new ResponseEntity<>(new MessageResponse(resultDelete), HttpStatus.OK);
    }

}
