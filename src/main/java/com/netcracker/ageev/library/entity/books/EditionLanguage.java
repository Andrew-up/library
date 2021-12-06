package com.netcracker.ageev.library.entity.books;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class EditionLanguage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String language;

}
