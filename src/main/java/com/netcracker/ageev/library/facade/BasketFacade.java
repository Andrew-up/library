package com.netcracker.ageev.library.facade;

import com.netcracker.ageev.library.dto.BasketUsersDTO;
import com.netcracker.ageev.library.model.users.BasketUser;
import com.netcracker.ageev.library.service.RentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BasketFacade {

    public BasketUsersDTO basketUsersDTO(BasketUser basketUser){
        BasketUsersDTO basketUsersDTO = new BasketUsersDTO();
        basketUsersDTO.setBasketId(basketUser.getBasketUserId());
        basketUsersDTO.setBookTitle(basketUser.getBooks().getBookTitle());
        basketUsersDTO.setBookId(basketUser.getBooks().getId());
        basketUsersDTO.setIsTheBasket(basketUser.getIsTheBasket());
        basketUsersDTO.setIsIssued(basketUser.getIsIssued());
        basketUsersDTO.setIsRequestCreated(basketUser.getIsRequestCreated());
        basketUsersDTO.setDateIssue(basketUser.getBookRentDateIssue());
        try {
            basketUsersDTO.setPriceId(basketUser.getBooks().getPrice().getId().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return basketUsersDTO;
    }
}
