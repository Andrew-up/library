package com.netcracker.ageev.library.model.books;

import com.netcracker.ageev.library.model.BaseEntity;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class BookGenres {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bookGenresId;

    @Column(nullable = false)
    private String genre;

}
