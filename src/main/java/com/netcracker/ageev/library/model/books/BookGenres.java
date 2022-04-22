package com.netcracker.ageev.library.model.books;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class BookGenres {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bookGenresId;

    @Column(nullable = false, unique = true)
    private String genre;

}
