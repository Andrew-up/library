package com.netcracker.ageev.library.controller;

import com.netcracker.ageev.library.dto.BookGenresDTO;
import com.netcracker.ageev.library.dto.SeriesDTO;
import com.netcracker.ageev.library.facade.SeriesFacade;
import com.netcracker.ageev.library.model.books.Series;
import com.netcracker.ageev.library.service.SeriesService;
import com.netcracker.ageev.library.validators.ResponseErrorValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books/series")
@CrossOrigin
public class SeriesController {


    private SeriesService seriesService;
    private SeriesFacade seriesFacade;
    private ResponseErrorValidator responseErrorValidator;

    @Autowired
    public SeriesController(SeriesService seriesService, SeriesFacade seriesFacade, ResponseErrorValidator responseErrorValidator) {
        this.seriesService = seriesService;
        this.seriesFacade = seriesFacade;
        this.responseErrorValidator = responseErrorValidator;
    }


    @GetMapping("/all")
    public ResponseEntity<List<SeriesDTO>> getAllSeries() {
        List<SeriesDTO> seriesDTOS = seriesService.getAllSeries()
                .stream()
                .map(seriesFacade::seriesDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(seriesDTOS, HttpStatus.OK);
    }

    @PostMapping("create")
    public ResponseEntity<Object> createSeries(@Valid @RequestBody SeriesDTO seriesDTO, BindingResult bindingResult, Principal principal){
        ResponseEntity<Object> listError = responseErrorValidator.mappedValidatorService(bindingResult);
        if (!ObjectUtils.isEmpty(listError)) return listError;
        Series series = seriesService.createSeries(seriesDTO,principal);
       SeriesDTO seriesDTO1 = seriesFacade.seriesDTO(series);
       return new ResponseEntity<>(seriesDTO1,HttpStatus.OK);
    }
}
