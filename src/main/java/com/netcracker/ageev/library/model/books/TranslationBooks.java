package com.netcracker.ageev.library.model.books;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class TranslationBooks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer translationId;

    @Column(nullable = false)
    private String translationName;
}
