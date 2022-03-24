package com.netcracker.ageev.library.dto;

import com.netcracker.ageev.library.model.users.Users;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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

    //name
    private String genreName;
    private String seriesName;
    private String translationName;
    private String authorsFullName;
    private String publisherName;
    private String coverName;
    private String ageLimitName;
    private String languageName;

    //Image

    private Integer imageToBookId;
//    private String series;
//    private Integer authorsName;
//    private Authors authors;

}
