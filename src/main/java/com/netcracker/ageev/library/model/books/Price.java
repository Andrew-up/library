package com.netcracker.ageev.library.model.books;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Price {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private Double priceRent;

//    @OneToOne(mappedBy = "price")
    private Long priceBook;

}
