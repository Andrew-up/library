package com.netcracker.ageev.library.service;

import com.netcracker.ageev.library.dto.SeriesDTO;
import com.netcracker.ageev.library.exception.DataNotFoundException;
import com.netcracker.ageev.library.model.books.Series;
import com.netcracker.ageev.library.repository.books.SeriesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SeriesService {

    public static final Logger LOG = LoggerFactory.getLogger(SeriesService.class);
    private final SeriesRepository seriesRepository;
    private final UsersService usersService;
    private final AuthorsService authorsService;

    @Autowired
    public SeriesService(SeriesRepository seriesRepository,
                         UsersService usersService,
                         AuthorsService authorsService) {
        this.seriesRepository = seriesRepository;
        this.usersService = usersService;
        this.authorsService = authorsService;
    }

    public List<Series> getAllSeries() {
        return seriesRepository.findAllByOrderBySeriesId();
    }

    public List<Series> getAllSeriesByAuthorsId(Integer authorsId) {
        List<Series> seriesList = new ArrayList<>();
        try {
            return seriesRepository.findAllByAuthorsId(authorsId).orElseThrow(() -> new DataNotFoundException("not found. getAllSeriesByAuthorsId: " + authorsId));
        } catch (DataNotFoundException throwable) {
            return seriesList;
        }
    }

    public Series createSeries(SeriesDTO seriesDTO, Principal principal) {
        ArrayList<String> arrayListError = isSeriesCorrect(seriesDTO);
        Series series = new Series();
        if (!ObjectUtils.isEmpty(arrayListError)) {
            series.setSeriesName(arrayListError.toString());
            return series;
        }

        return saveSeries(seriesDTO, series);

    }


    public Series getSeriesById(Integer id) {
        try {
            return seriesRepository.findSeriesBySeriesId(id).orElseThrow(() -> new DataNotFoundException("not found. getSeriesById"+ id));
        } catch (DataNotFoundException e) {
            return null;
        }
    }

    public String deleteSeries(Integer seriesId, Principal principal) {
        Optional<Series> delete = seriesRepository.findSeriesBySeriesId(seriesId);
        delete.ifPresent(seriesRepository::delete);
        return "Тип обложки с id: " + seriesId + " удалено";

    }

    private ArrayList<String> isSeriesCorrect(SeriesDTO seriesDTO) {
        ArrayList<String> listError = new ArrayList<>();
        String regex = "^\\s*$";
        boolean result = seriesDTO.getSeriesName().matches(regex);
        if (result) {
            LOG.info("Error when adding to the database, such an entry already exists. isSeriesCorrect :" + seriesDTO.getSeriesName());
            listError.add("Выражение не прошло проверку по формату записи");
        }
        return listError;
    }

    private Series saveSeries(SeriesDTO seriesDTO, Series series) {
        try {
            series.setSeriesName(seriesDTO.getSeriesName());
            series.setAuthors(authorsService.getAuthorsById(seriesDTO.getAuthorsId()));
            System.out.println("series: " + series);
            seriesRepository.save(series);
            return series;
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            series.setSeriesId(-2000);
            series.setSeriesName("Ошибка при добавлении в бд, не выбран автор");
            return series;
        }
    }

    public Series updateSeries(SeriesDTO seriesDTO, Principal principal) {
        ArrayList<String> arrayListError = isSeriesCorrect(seriesDTO);
        Series series = seriesRepository.findSeriesBySeriesId(seriesDTO.getSeriesId()).orElseThrow(() -> new DataNotFoundException("Series not found. updateSeries: "+ seriesDTO.getSeriesId()));
        if (!ObjectUtils.isEmpty(arrayListError)) {
            series.setSeriesName(arrayListError.toString());
            return series;
        }

        return saveSeries(seriesDTO, series);

    }

}
