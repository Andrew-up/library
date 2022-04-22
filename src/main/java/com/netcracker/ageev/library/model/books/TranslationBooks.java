package com.netcracker.ageev.library.model.books;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Getter
@Setter
@Entity
public class TranslationBooks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer translationId;

    @Column(nullable = false)
    private String translationName;
}
