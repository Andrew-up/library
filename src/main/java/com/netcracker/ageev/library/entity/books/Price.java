package com.netcracker.ageev.library.entity.books;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Double priceRent;
}
