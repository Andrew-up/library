package com.netcracker.ageev.library.service;

import com.netcracker.ageev.library.dto.SeriesDTO;
import com.netcracker.ageev.library.model.books.Series;
import com.netcracker.ageev.library.repository.books.SeriesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class SeriesService {

    public static final Logger LOG = LoggerFactory.getLogger(SeriesService.class);
    private final SeriesRepository seriesRepository;

    @Autowired
    public SeriesService(SeriesRepository seriesRepository) {
        this.seriesRepository = seriesRepository;
    }

    public List<Series> getAllSeries(){
        return seriesRepository.findAllByOrderBySeriesId();
    }
    public Series createSeries(SeriesDTO seriesDTO, Principal principal){
        Series series = new Series();
        ArrayList<String> arrayListError =  isSeriesCorrect(seriesDTO);
        if (!ObjectUtils.isEmpty(arrayListError)){
            series.setSeriesName(arrayListError.toString());
            return series;
        }
        series.setSeriesName(seriesDTO.getSeriesName());
        return seriesRepository.save(series);
    }
    private ArrayList<String> isSeriesCorrect(SeriesDTO seriesDTO){
        ArrayList<String> listError = new ArrayList<>();
        if(seriesDTO.getSeriesName().isEmpty()){
            listError.add("Имя серии не корректно");
        }
        return listError;
    }

}
