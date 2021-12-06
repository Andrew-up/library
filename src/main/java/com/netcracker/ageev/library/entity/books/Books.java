package com.netcracker.ageev.library.entity.books;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Books  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY,targetEntity = Authors.class,cascade = CascadeType.ALL)
    private Authors authors;

    @Column(nullable = false, columnDefinition = "varchar(100)")
    private String bookTitle;

    @ManyToOne(targetEntity = BookGenres.class,fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    private BookGenres genreCode;

    @Column(nullable = false,columnDefinition = "date")
    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date releaseDate;

    @Column(nullable = false)
    private Integer numberPages;

    @ManyToOne(targetEntity = Publisher.class,fetch = FetchType.LAZY)
    @JoinColumn(name = "publisherId")
    private Publisher publisherId;

    @Column(nullable = true)
    private String series;

    @ManyToOne(targetEntity = CoverCode.class,fetch = FetchType.LAZY)
    @JoinColumn(name = "coverId")
    private CoverCode coverId;

    @Column(nullable = true)
    private String ISBN;

    @ManyToOne(fetch = FetchType.LAZY,targetEntity = AgeLimit.class)
    private AgeLimit ageLimitCode;

    @ManyToOne(targetEntity = EditionLanguage.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "languageId")
    private EditionLanguage languageId;

    @Column(nullable = true)
    private String translation;

    public Books() {

    }

}
