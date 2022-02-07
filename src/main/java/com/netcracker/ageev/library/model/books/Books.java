package com.netcracker.ageev.library.model.books;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Books implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "authors_id")
    @ManyToOne
    private Authors authors;

    @Column(nullable = false, columnDefinition = "varchar(100)")
    private String bookTitle;

    @ManyToOne(targetEntity = BookGenres.class, fetch = FetchType.LAZY)
    @JoinColumn(nullable = true)
    private BookGenres genreCode;

    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-mm-dd")
    private String releaseDate;

    @ManyToOne(targetEntity = Publisher.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "publisherId")
    private Publisher publisherId;

    @Column(nullable = false)
    private Integer numberPages;

    @ManyToOne(targetEntity = CoverCode.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "coverId")
    private CoverCode coverId;

    @ManyToOne(targetEntity = Series.class,fetch = FetchType.LAZY)
    private Series series;

    @Column(nullable = true)
    private String ISBN;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = AgeLimit.class)
    private AgeLimit ageLimitCode;

    @ManyToOne(targetEntity = EditionLanguage.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "languageId")
    private EditionLanguage languageId;

    @ManyToOne(targetEntity = TranslationBooks.class, fetch = FetchType.LAZY)
    private TranslationBooks translation;

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
    public Authors getAuthors(){
        Authors authors = new Authors();
        try {
            if (authors.getFirstname() != null) {
                authors.setId(authors.getId());
            }
            return authors;
        } catch (NullPointerException e) {
            authors.setFirstname("Not found author");
            return authors;
        }
    }

    public Series getSeries(){
        Series seriesObj = new Series();
        try {
            if (series.getSeriesName() != null) {
                seriesObj.setSeriesName(series.getSeriesName());
            }
            return series;
        } catch (NullPointerException e) {
            seriesObj.setSeriesName("Not found series");
            return seriesObj;
        }
    }

    public TranslationBooks getTranslation(){
        TranslationBooks translationBooks = new TranslationBooks();
        try {
            if(translation.getTranslationName()!=null){
                translationBooks.setTranslationName(translation.getTranslationName());
            }
            return translation;
        }
        catch (NullPointerException e){
            translationBooks.setTranslationName("Not found translation");
            return translationBooks;
        }
    }

}
