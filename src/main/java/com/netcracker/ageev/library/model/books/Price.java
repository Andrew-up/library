package com.netcracker.ageev.library.model.books;

import com.netcracker.ageev.library.model.BaseEntity;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Price extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Double priceRent;
}
