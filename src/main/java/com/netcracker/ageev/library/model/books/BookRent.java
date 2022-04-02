package com.netcracker.ageev.library.model.books;

import com.netcracker.ageev.library.model.BaseEntity;
import com.netcracker.ageev.library.model.users.BasketUser;
import com.netcracker.ageev.library.model.users.Employee;
import com.netcracker.ageev.library.model.users.Users;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class BookRent  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Books.class)
    @JoinColumn(name = "booksId")
    private Books booksId;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Employee.class)
    @JoinColumn(name = "employeeId")
    private Employee employeeId;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Users.class)
    @JoinColumn(name = "usersId")
    private Users usersId;

    @Column(nullable = true)
    private String dateIssue;

    @Column(nullable = true)
    private String dateReturn;

    @ManyToOne
    private BasketUser basketUser;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Price.class)
    @JoinColumn(name = "priceId",nullable = true)
    private Price priceId;

    public Books getBooksId() {
        Books books = new Books();
        try {
            if(booksId.getId()!=null){
                books.setId(booksId.getId());
            }
            return booksId;
        }catch (NullPointerException e){
            books.setBookTitle("Книга не найдена");
            return books;
        }
    }


    public Users getUsersId() {
        Users users = new Users();
        try {
            if(usersId.getId()!=null){
                users.setId(usersId.getId());
            }
            return usersId;
        }catch (NullPointerException e){
            users.setFirstname("Юзер не найден");
            return users;
        }
    }

    public Price getPriceId() {
        Price price = new Price();
        try {
            if(priceId.getId()!=null){
                price.setId(priceId.getId());
            }
            return priceId;
        }catch (NullPointerException e){
            price.setPriceRent(0.0);
            return price;
        }
    }

}
