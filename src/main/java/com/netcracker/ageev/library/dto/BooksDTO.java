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
    private String authorsName;
    private Integer languageId;
    private Integer publisherId;
    private Integer ageLimitCode;

    //name
    private String genreName;
    private String seriesName;
    private String translationName;
//    private String series;
//    private Integer authorsName;
//    private Authors authors;

}
