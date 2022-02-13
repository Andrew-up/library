package com.netcracker.ageev.library.controller;

import com.netcracker.ageev.library.dto.AgeLimitDTO;
import com.netcracker.ageev.library.dto.PriceRentDTO;
import com.netcracker.ageev.library.facade.PriceRentFacade;
import com.netcracker.ageev.library.model.books.AgeLimit;
import com.netcracker.ageev.library.model.books.Price;
import com.netcracker.ageev.library.service.PriceService;
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
@RequestMapping("/api/books/price")
@CrossOrigin
public class PriceController {

    @Autowired
    private PriceRentFacade priceFacade;

    @Autowired
    private PriceService priceService;

    @Autowired
    private ResponseErrorValidator responseErrorValidator;

    @GetMapping("/all")
    public ResponseEntity<List<PriceRentDTO>> getAllPrice(){
        List<PriceRentDTO> priceRentDTOList = priceService.getAllPrice()
                .stream()
                .map(priceFacade::priceDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(priceRentDTOList, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createPrice(@Valid @RequestBody PriceRentDTO priceRentDTO,
                                                 BindingResult bindingResult, Principal principal){
        ResponseEntity<Object> listError = responseErrorValidator.mappedValidatorService(bindingResult);
        if (!ObjectUtils.isEmpty(listError)) return  listError;
        Price price = priceService.createPrice(priceRentDTO,principal);
        PriceRentDTO priceRentDTO1 = priceFacade.priceDTO(price);
        return new ResponseEntity<>(priceRentDTO1,HttpStatus.OK);
    }

}
