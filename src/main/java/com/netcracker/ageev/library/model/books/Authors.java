package com.netcracker.ageev.library.model.books;


import com.netcracker.ageev.library.model.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity

public class Authors implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "authors",targetEntity = Books.class)
    private List<Books> books = new ArrayList<>();

    @Column(nullable = false, columnDefinition = "varchar(50)")
    private String firstname;

    @Column(nullable = false, columnDefinition = "varchar(50)")
    private String lastname;

    @Column(nullable = true, columnDefinition = "varchar(50)")
    private String patronymic;

    @Column(nullable = true, columnDefinition = "date")
    private String dateOfBirth;



}
