package com.netcracker.ageev.library.model.books;


import lombok.Getter;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Authors {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "authors", targetEntity = Books.class)
    private List<Books> books = new ArrayList<>();

    @Column(nullable = false, columnDefinition = "varchar(50)")
    private String firstname;

    @Column(nullable = false, columnDefinition = "varchar(50)")
    private String lastname;

    @Column(nullable = true, columnDefinition = "varchar(50)")
    private String patronymic;

    @Column(nullable = true)
    private String dateOfBirth;

    @Column(nullable = true)
    private Integer seriesId;





}
