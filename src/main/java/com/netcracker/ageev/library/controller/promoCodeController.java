package com.netcracker.ageev.library.controller;

import com.netcracker.ageev.library.dto.AgeLimitDTO;
import com.netcracker.ageev.library.dto.PromoCodeDTO;
import com.netcracker.ageev.library.model.books.AgeLimit;
import com.netcracker.ageev.library.service.PromoCodeService;
import com.netcracker.ageev.library.validators.ResponseErrorValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/promocode")
@CrossOrigin
@PermitAll()
public class promoCodeController {

    @Autowired
    private ResponseErrorValidator responseErrorValidator;
    @Autowired
    private PromoCodeService promoCodeService;

    public promoCodeController(ResponseErrorValidator responseErrorValidator, PromoCodeService promoCodeService) {
        this.responseErrorValidator = responseErrorValidator;
        this.promoCodeService = promoCodeService;
    }

    @PostMapping("/apply")
    public ResponseEntity<Object> applyPromoCode(@Valid @RequestBody PromoCodeDTO promoCodeDTO, BindingResult bindingResult, Principal principal) {
        ResponseEntity<Object> listError = responseErrorValidator.mappedValidatorService(bindingResult);
        if (!ObjectUtils.isEmpty(listError)) return listError;
//        AgeLimit ageLimit = ageLimitService.createAgeLimit(ageLimitDTO,principal);
//        AgeLimitDTO ageLimitCreated = limitFacade.ageLimitDTO(ageLimit);
        System.out.println("        obj:    " + promoCodeDTO.getPromoCode());
        System.out.println("        principal:    " + principal);
        System.out.println("        bindingResult:    " + bindingResult);
        PromoCodeDTO promoCodeDTO1 = new PromoCodeDTO();
        promoCodeDTO1.setPromoCode(promoCodeService.whatItPromoCode(promoCodeDTO.getPromoCode(),principal));
        return new ResponseEntity<>(promoCodeDTO1, HttpStatus.OK);
//        return new ResponseEntity<>(ageLimitCreated, HttpStatus.OK);
    }

}
