package com.netcracker.ageev.library.facade;

import com.netcracker.ageev.library.dto.SeriesDTO;
import com.netcracker.ageev.library.model.books.Authors;
import com.netcracker.ageev.library.model.books.Series;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SeriesFacade {

    public SeriesDTO seriesDTO(Series series) {
        SeriesDTO seriesDTO = new SeriesDTO();
        Authors authors = new Authors();
        seriesDTO.setSeriesId(series.getSeriesId());
        seriesDTO.setSeriesName(series.getSeriesName());
        authors.setId(series.getAuthors().getId());
        authors.setFirstname(series.getAuthors().getFirstname());
        authors.setLastname(series.getAuthors().getLastname());
        authors.setPatronymic(series.getAuthors().getPatronymic());
        authors.setDateOfBirth(series.getAuthors().getDateOfBirth());
        seriesDTO.setAuthors(authors);
        return seriesDTO;
    }


}
