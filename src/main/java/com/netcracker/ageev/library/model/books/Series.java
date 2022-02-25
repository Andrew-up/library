package com.netcracker.ageev.library.model.books;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Series {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer seriesId;

    @Column(nullable = false)
    private String seriesName;

    @ManyToOne
    @JoinColumn(name = "authors_id")
    private Authors authors;

}
