package com.netcracker.ageev.library.model.books;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

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

    @ManyToOne(targetEntity = CoverBook.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "coverId")
    private CoverBook coverId;

    @ManyToOne(targetEntity = Series.class, fetch = FetchType.LAZY)
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

    private String fullName;

    @Column(name = "ImageId")
    private String imageId;

    //    private Integer countBooks;
    @ManyToOne()
    private Price price;

    private Integer countBooks;

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

    public Authors getAuthors() {
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

    public Series getSeries() {
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

    public TranslationBooks getTranslation() {
        TranslationBooks translationBooks = new TranslationBooks();
        try {
            if (translation.getTranslationName() != null) {
                translationBooks.setTranslationName(translation.getTranslationName());
            }
            return translation;
        } catch (NullPointerException e) {
            translationBooks.setTranslationName("Not found translation");
            return translationBooks;
        }
    }

    public Publisher getPublisherId() {
        Publisher publisher = new Publisher();
        try {
            if (publisherId.getName() != null) {
                publisher.setName(publisher.getName());
            }
            return publisherId;
        } catch (NullPointerException e) {
            publisher.setName("Not found publisher");
            return publisher;
        }
    }

    public CoverBook getCoverId() {
        CoverBook coverBook = new CoverBook();
        try {
            if (coverId.getName() != null) {
                coverBook.setName(coverId.getName());
            }
            return coverId;
        } catch (NullPointerException e) {
            coverBook.setName("Not found coverCode");
            return coverBook;
        }
    }

    public AgeLimit getAgeLimitCode() {
        AgeLimit ageLimit = new AgeLimit();
        try {
            if (ageLimitCode.getAge() != null) {
                ageLimit.setAge(ageLimitCode.getAge());
            }
            return ageLimitCode;
        } catch (NullPointerException e) {
            ageLimit.setAge("Not found ageLimit");
            return ageLimit;
        }
    }

    public EditionLanguage getLanguageId() {
        EditionLanguage editionLanguage = new EditionLanguage();
        try {
            if (languageId.getLanguageName() != null) {
                editionLanguage.setLanguageName(languageId.getLanguageName());
            }
            return languageId;
        } catch (NullPointerException e) {
            editionLanguage.setLanguageName("Not found edition language");
            return editionLanguage;
        }
    }


    public String getFullName() {
        return this.authors.getFirstname() + " " + this.authors.getLastname() + " " + this.authors.getPatronymic();
    }

}
