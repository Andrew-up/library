package com.netcracker.ageev.library.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BooksDTO {

    private Long bookId;
    private String bookTitle;
    private String bookReleaseDate;
    private Integer numberPages;
    private Integer bookSeries;
    private String nameISBN;
    private Integer translationId;
    private Integer genreCode;
    private Integer authors;
    private Integer languageId;
    private Integer publisherId;
    private Integer ageLimitCode;
    private Integer coverId;
    private Integer priceId;
    private Integer countBooks;

    //name
    private String genreName;
    private String seriesName;
    private String translationName;
    private String authorsFullName;
    private String publisherName;
    private String coverName;
    private String ageLimitName;
    private String languageName;
    private String priceName;


    //Image

    private Integer imageToBookId;

}
