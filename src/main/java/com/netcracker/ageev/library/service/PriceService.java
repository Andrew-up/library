package com.netcracker.ageev.library.service;


import com.netcracker.ageev.library.dto.AgeLimitDTO;
import com.netcracker.ageev.library.dto.PriceRentDTO;
import com.netcracker.ageev.library.model.books.AgeLimit;
import com.netcracker.ageev.library.model.books.BookRent;
import com.netcracker.ageev.library.model.books.Price;
import com.netcracker.ageev.library.model.users.Employee;
import com.netcracker.ageev.library.model.users.Users;
import com.netcracker.ageev.library.repository.books.BookRentRepository;
import com.netcracker.ageev.library.repository.books.BooksRepository;
import com.netcracker.ageev.library.repository.books.PriceRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;

import java.security.Principal;
import java.util.List;

@Service
public class PriceService {

    public static final Logger LOG = LoggerFactory.getLogger(PriceService.class);

    private final BookRentRepository bookRentRepository;
    private final PriceRepository priceRepository;

    @Autowired
    public PriceService(BookRentRepository bookRentRepository, PriceRepository priceRepository) {
        this.bookRentRepository = bookRentRepository;
        this.priceRepository = priceRepository;
    }

    public List<Price> getAllPrice(){
       return priceRepository.findAllByOrderById();
    }

    public Price getPriceById(Integer id){
        try {
            return priceRepository.findPriceById(id).orElseThrow(() -> new NullPointerException("not found"));
        } catch (NullPointerException e) {
            return null;
        }
    }

    public Price createPrice(PriceRentDTO priceRentDTO, Principal principal){
        Price price = new Price();
        price.setPriceRent(priceRentDTO.getPriceName());
//        ageLimit.setCreated(ageLimit.getCreated());
//        ageLimit.setUpdated(ageLimitDTO.getUpdated());
//        LOG.info("Create Age Limit for user:{}", users.getEmail());
        return priceRepository.save(price);
    }


}
