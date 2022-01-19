package com.netcracker.ageev.library.model.books;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity

public class Authors {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "authors", targetEntity = Books.class)
    private List<Books> books = new ArrayList<>();

    @Column(nullable = false, columnDefinition = "varchar(50)")
    private String firstname;

    @Column(nullable = false, columnDefinition = "varchar(50)")
    private String lastname;

    @Column(nullable = true, columnDefinition = "varchar(50)")
    private String patronymic;

    //    @Column(nullable = true, columnDefinition = "date")
//    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-mm-yyyy")
    private String dateOfBirth;
//
//    private String errorList;
}
