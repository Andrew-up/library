package com.netcracker.ageev.library.facade;

import com.netcracker.ageev.library.dto.PriceRentDTO;
import com.netcracker.ageev.library.entity.books.BookRent;
import org.springframework.stereotype.Component;

@Component
public class PriceRentFacade {

    public PriceRentDTO priceToPriceDTO(BookRent bookRent){
        PriceRentDTO priceRentDTO = new PriceRentDTO();
        priceRentDTO.setId(bookRent.getId());
        return priceRentDTO;
    }
}
