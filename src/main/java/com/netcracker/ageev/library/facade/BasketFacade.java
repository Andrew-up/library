package com.netcracker.ageev.library.facade;

import com.netcracker.ageev.library.dto.BasketUsersDTO;
import com.netcracker.ageev.library.model.users.BasketUser;
import org.springframework.stereotype.Component;

@Component
public class BasketFacade {

    public BasketUsersDTO basketUsersDTO(BasketUser basketUser){
        BasketUsersDTO basketUsersDTO = new BasketUsersDTO();
        basketUsersDTO.setBasketId(basketUser.getBasketUserId());
        basketUsersDTO.setUsersName(basketUser.getUsers().getFirstname()+" "+basketUser.getUsers().getLastname());
        basketUsersDTO.setBookTitle(basketUser.getBooks().getBookTitle());
        basketUsersDTO.setBookId(basketUser.getBooks().getId());
        try {
            basketUsersDTO.setPriceId(basketUser.getBooks().getPrice().getId().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
//        basketUsersDTO.setPriceName(basketUser.getBooks().getPrice().getPriceRent().toString());
        return basketUsersDTO;
    }
}
