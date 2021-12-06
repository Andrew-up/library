package com.netcracker.ageev.library.controller;

import com.netcracker.ageev.library.dto.PriceRentDTO;
import com.netcracker.ageev.library.facade.PriceRentFacade;
import com.netcracker.ageev.library.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/all")
    public ResponseEntity<List<PriceRentDTO>> getAllPrice(){
        List<PriceRentDTO> priceRentDTOList = priceService.getAllPrice()
                .stream()
                .map(priceFacade::priceToPriceDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(priceRentDTOList, HttpStatus.OK);
    }

}
