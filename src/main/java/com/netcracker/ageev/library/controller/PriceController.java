package com.netcracker.ageev.library.controller;

import com.netcracker.ageev.library.dto.PriceRentDTO;
import com.netcracker.ageev.library.facade.PriceRentFacade;
import com.netcracker.ageev.library.payload.responce.MessageResponse;
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

        String resultCreate = priceService.createPrice(priceRentDTO,principal);
        return new ResponseEntity<>(new MessageResponse(resultCreate),HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<Object> updatePrice(@Valid @RequestBody PriceRentDTO priceRentDTO, BindingResult bindingResult, Principal principal){
        ResponseEntity<Object> listError = responseErrorValidator.mappedValidatorService(bindingResult);
        if (!ObjectUtils.isEmpty(listError)) return listError;
        String resultUpdate = priceService.updatePrice(priceRentDTO,principal);
        return new ResponseEntity<>(new MessageResponse(resultUpdate),HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity<Object> deletePrice(@Valid @RequestBody String id, Principal principal){
        String resultDelete = priceService.deletePrice(Integer.parseInt(id),principal);
        return new ResponseEntity<>(new MessageResponse(resultDelete),HttpStatus.OK);
    }


}
