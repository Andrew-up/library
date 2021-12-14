package com.netcracker.ageev.library.dto;

import com.netcracker.ageev.library.model.books.*;
import lombok.Data;

import java.util.Date;

@Data
public class BooksDTO {

    private String bookTitle;
    private String releaseDate;
    private Integer numberPages;
    private String series;
    private String ISBN;
    private String translation;
    private String genreCode;

}
