package com.netcracker.ageev.library.model.users;

import com.netcracker.ageev.library.model.books.Books;
import com.netcracker.ageev.library.model.books.Price;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class BasketUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long basketUserId;

    @ManyToOne(targetEntity = Books.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "books_id")
    private Books books;

    @OneToOne(targetEntity = Price.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "price_id")
    private Price price;
    private Long usersId;
    private Boolean isTheBasket;
    private Boolean isRequestCreated;
    private Boolean isIssued;
    private String bookRentDateIssue;

}
