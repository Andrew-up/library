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
    private String bookSeries;
    private String nameISBN;
    private String translation;
    private String genreCode;
    private Integer authors;
    private String authorsName;
    private Integer languageId;
    private Integer publisherId;
    private Integer ageLimitCode;
//    private String series;
//    private Integer authorsName;
//    private Authors authors;

}
