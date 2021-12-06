package com.netcracker.ageev.library.entity.books;

import com.netcracker.ageev.library.entity.users.Employee;
import com.netcracker.ageev.library.entity.users.Users;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class BookRent {

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
    private Date dateIssue;

    @Column(nullable = true)
    private Date dateReturn;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Price.class)
    @JoinColumn(name = "priceId",nullable = true)
    private Price priceId;


}
