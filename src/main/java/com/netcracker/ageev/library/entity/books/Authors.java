package com.netcracker.ageev.library.entity.books;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
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
    private Date dateOfBirth;

}
