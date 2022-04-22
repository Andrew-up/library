package com.netcracker.ageev.library.model.books;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Series {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer seriesId;

    @Column(nullable = false)
    private String seriesName;

    @ManyToOne
    @JoinColumn(name = "authors_id", nullable = false)
    private Authors authors;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = Books.class)
    private Books books;

}
