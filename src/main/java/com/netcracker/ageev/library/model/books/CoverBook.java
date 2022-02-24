package com.netcracker.ageev.library.model.books;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class CoverBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false,unique = true)
    private String name;
}
