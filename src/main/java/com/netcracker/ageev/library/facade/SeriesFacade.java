package com.netcracker.ageev.library.facade;

import com.netcracker.ageev.library.dto.SeriesDTO;
import com.netcracker.ageev.library.model.books.Series;
import org.springframework.stereotype.Component;

@Component
public class SeriesFacade {

    public SeriesDTO seriesDTO(Series series){

        SeriesDTO seriesDTO = new SeriesDTO();
        seriesDTO.setSeriesId(series.getSeriesId());
        seriesDTO.setSeriesName(series.getSeriesName());
        return seriesDTO;
    }
}
