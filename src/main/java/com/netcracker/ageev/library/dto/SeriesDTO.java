package com.netcracker.ageev.library.dto;

import com.netcracker.ageev.library.model.books.Authors;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SeriesDTO {

    private Integer seriesId;
    private String seriesName;
    private Authors authors;
    private Integer authorsId;

}
