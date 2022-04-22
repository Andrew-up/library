package com.netcracker.ageev.library.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasketUsersDTO {

    private Long basketId;
    private String bookTitle;
    private String usersName;
    private Long bookId;
    private String priceId;
    private String priceName;
    private String userId;
    private Boolean isTheBasket;
    private Boolean isRequestCreated;
    private Boolean isIssued;
    private String dateIssue;

}
