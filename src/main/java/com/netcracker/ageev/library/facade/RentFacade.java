package com.netcracker.ageev.library.facade;

import com.netcracker.ageev.library.dto.RentDTO;
import com.netcracker.ageev.library.model.books.BookRent;
import org.springframework.stereotype.Component;

@Component
public class RentFacade {

    public RentDTO rentDTO(BookRent bookRent){
        RentDTO rentDTO = new RentDTO();
        rentDTO.setId(bookRent.getId());
        rentDTO.setDateIssue(bookRent.getDateIssue());
        rentDTO.setDateReturn(bookRent.getDateReturn());
        rentDTO.setBookId(bookRent.getBooksId().getId());
        rentDTO.setEmployeeId(bookRent.getEmployeeId().getId());
        rentDTO.setPriceId(bookRent.getPriceId().getId());
        rentDTO.setUserId(bookRent.getUsersId().getId());

        //name
        rentDTO.setEmployeeName(bookRent.getEmployeeId().getUsers().getEmail());
        rentDTO.setBookName(bookRent.getBooksId().getBookTitle());
        rentDTO.setPriceName(bookRent.getPriceId().getPriceRent().toString());
        rentDTO.setUserName(bookRent.getUsersId().getEmail());
        return rentDTO;
    }
}
