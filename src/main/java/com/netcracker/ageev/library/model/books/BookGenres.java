package com.netcracker.ageev.library.model.books;

import com.netcracker.ageev.library.model.BaseEntity;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class BookGenres extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String genre;

}
