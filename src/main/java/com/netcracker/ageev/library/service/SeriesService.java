package com.netcracker.ageev.library.service;

import com.netcracker.ageev.library.dto.CoverBookDTO;
import com.netcracker.ageev.library.dto.SeriesDTO;
import com.netcracker.ageev.library.model.books.Authors;
import com.netcracker.ageev.library.model.books.BookGenres;
import com.netcracker.ageev.library.model.books.CoverBook;
import com.netcracker.ageev.library.model.books.Series;
import com.netcracker.ageev.library.model.users.Users;
import com.netcracker.ageev.library.repository.books.AuthorsRepository;
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
        List<Series> seriesList= new ArrayList<>();
        try {
            return seriesRepository.findAllByAuthorsId(authorsId).orElseThrow(() -> new NullPointerException("not found"));
        }
        catch (Throwable throwable){
            System.out.println("seriesList: "+seriesList);
            return seriesList;
        }

    }

    public Series createSeries(SeriesDTO seriesDTO, Principal principal) {
        Users users = usersService.getUserByPrincipal(principal);
        ArrayList<String> arrayListError = isSeriesCorrect(seriesDTO);
        Series series = new Series();

        if (!ObjectUtils.isEmpty(arrayListError)) {
            series.setSeriesName(arrayListError.toString());
            return series;
        }
        if (usersService.DataAccessToUser(users)) {
            return saveSeries(seriesDTO, series);
        } else {
            series.setSeriesName("Для пользователя с ролью " + users.getERole() + " добавление невозможно");
            return series;
        }
    }


    public Series getSeriesById(Integer id) {
        try {
            return seriesRepository.findSeriesBySeriesId(id).orElseThrow(() -> new NullPointerException("not found"));
        } catch (NullPointerException e) {
            return null;
        }
    }

    public String deleteSeries(Integer seriesId, Principal principal) {

        Users users = usersService.getUserByPrincipal(principal);
        if (usersService.DataAccessToUser(users)) {
            Optional<Series> delete = seriesRepository.findSeriesBySeriesId(seriesId);
            delete.ifPresent(seriesRepository::delete);
            return "Тип обложки с id: " + seriesId + " удалено";
        } else {
            return "Для пользователя с ролью " + users.getERole() + " удаление невозможно";
        }
    }

    private ArrayList<String> isSeriesCorrect(SeriesDTO seriesDTO) {
        ArrayList<String> listError = new ArrayList<>();
        String regex = "^\\s*$";
        boolean result = seriesDTO.getSeriesName().matches(regex);
        if (result) {
            listError.add("Выражение не прошло проверку по формату записи");
        }
        return listError;
    }

    private Series saveSeries(SeriesDTO seriesDTO, Series series) {
        try {
            series.setSeriesName(seriesDTO.getSeriesName());
            series.setAuthors(authorsService.getAuthorsById(seriesDTO.getAuthorsId()));
            System.out.println("series: "+series);
            seriesRepository.save(series);
            return series;
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            series.setSeriesId(-2000);
            series.setSeriesName("Ошибка при добавлении в бд, такая запись уже есть");
            return series;
        }
    }

    public Series updateSeries(SeriesDTO seriesDTO, Principal principal) {
        Users users = usersService.getUserByPrincipal(principal);
        ArrayList<String> arrayListError = isSeriesCorrect(seriesDTO);
        Series series = seriesRepository.findSeriesBySeriesId(seriesDTO.getSeriesId()).orElseThrow(() -> new UsernameNotFoundException("Series not found"));
        if (!ObjectUtils.isEmpty(arrayListError)) {
            series.setSeriesName(arrayListError.toString());
            return series;
        }
        if (usersService.DataAccessToUser(users)) {
            return saveSeries(seriesDTO, series);
        } else {
            series.setSeriesName("Для пользователя с ролью " + users.getERole() + " обновление невозможно");
            return series;
        }
    }

}
