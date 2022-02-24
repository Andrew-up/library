package com.netcracker.ageev.library.facade;

import com.netcracker.ageev.library.dto.PriceRentDTO;
import com.netcracker.ageev.library.model.books.Price;
import org.springframework.stereotype.Component;

@Component
public class PriceRentFacade {

    public PriceRentDTO priceDTO(Price price){
        PriceRentDTO priceRentDTO = new PriceRentDTO();
        priceRentDTO.setId(price.getId());
        priceRentDTO.setPriceName(price.getPriceRent().toString());
        return priceRentDTO;
    }
}
