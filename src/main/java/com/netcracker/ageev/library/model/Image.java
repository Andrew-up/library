package com.netcracker.ageev.library.model;

import com.netcracker.ageev.library.model.books.Books;
import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;

@Entity
@Data
public class Image  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    @JsonIgnore
    private Long booksId;

    @JsonIgnore
    private Long usersId;
}
