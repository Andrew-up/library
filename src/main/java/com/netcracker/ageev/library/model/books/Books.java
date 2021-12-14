package com.netcracker.ageev.library.model.books;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.netcracker.ageev.library.model.BaseEntity;
import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Books {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @ManyToOne(fetch = FetchType.LAZY,targetEntity = Authors.class,cascade = CascadeType.ALL)
//    @OneToOne(fetch = FetchType.LAZY,targetEntity = Authors.class,mappedBy = "id")



    @JoinColumn(name = "authors")
    private Integer authors;

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
    @JsonIgnore
    @Column(nullable = true)
    private String series;

    @JsonProperty(value = "series")
    public String getSeries(){
        return series;
    }

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
