package com.netcracker.ageev.library.model.books;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JoinColumn(name = "authors")
    private Integer authors;

    @Column(nullable = false, columnDefinition = "varchar(100)")
    private String bookTitle;

    @ManyToOne(targetEntity = BookGenres.class, fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    private BookGenres genreCode;

    @Column(nullable = false, columnDefinition = "date")
    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date releaseDate;

    @ManyToOne(targetEntity = Publisher.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "publisherId")
    private Publisher publisherId;

    @Column(nullable = false)
    private Integer numberPages;

    @ManyToOne(targetEntity = CoverCode.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "coverId")
    private CoverCode coverId;

    @Column(nullable = true)
    private String series;

    @Column(nullable = true)
    private String ISBN;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = AgeLimit.class)
    private AgeLimit ageLimitCode;

    @ManyToOne(targetEntity = EditionLanguage.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "languageId")
    private EditionLanguage languageId;

    @Column(nullable = true)
    private String translation;

    @Column(name = "ImageId")
    private String imageId;

    //    @Column(nullable = true,name = "imageBookId")


    public Books() {

    }

    public BookGenres getGenreCode() {
        BookGenres bookGenres = new BookGenres();
        try {
            if (genreCode.getGenre() != null) {
                bookGenres.setGenre(genreCode.getGenre());
            }
            return genreCode;
        } catch (NullPointerException e) {
            bookGenres.setGenre("Not found genre");
            return bookGenres;
        }
    }

}
