package com.netcracker.ageev.library.controller;

import com.netcracker.ageev.library.dto.PublisherDTO;
import com.netcracker.ageev.library.facade.PublisherFacade;
import com.netcracker.ageev.library.model.books.Publisher;
import com.netcracker.ageev.library.payload.responce.MessageResponse;
import com.netcracker.ageev.library.service.PublisherService;
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
@RequestMapping("/api")
@CrossOrigin
public class PublisherController {


    private final PublisherFacade publisherFacade;
    private final PublisherService publisherService;
    private final ResponseErrorValidator responseErrorValidator;

    @Autowired
    public PublisherController(PublisherFacade publisherFacade,
                               PublisherService publisherService,
                               ResponseErrorValidator responseErrorValidator) {
        this.publisherFacade = publisherFacade;
        this.publisherService = publisherService;
        this.responseErrorValidator = responseErrorValidator;
    }

    @GetMapping("/books/publisher/all")
    public ResponseEntity<List<PublisherDTO>> getAllPublisher() {
        List<PublisherDTO> publisherDTOS = publisherService.getAllPublisher()
                .stream()
                .map(publisherFacade::publisherDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(publisherDTOS, HttpStatus.OK);

    }

    @PostMapping("/staff/books/publisher/create")
    public ResponseEntity<Object> createPublisher(@Valid @RequestBody PublisherDTO publisherDTO,
                                                  BindingResult bindingResult,
                                                  Principal principal) {
        ResponseEntity<Object> listError = responseErrorValidator.mappedValidatorService(bindingResult);
        if (!ObjectUtils.isEmpty(listError)) return listError;
        Publisher publisher = publisherService.createPublisher(publisherDTO, principal);
        PublisherDTO publisherDTO1 = publisherFacade.publisherDTO(publisher);
        return new ResponseEntity<>(publisherDTO1, HttpStatus.OK);
    }

    @PostMapping("/staff/books/publisher/update")
    public ResponseEntity<Object> updatePublisher(@Valid @RequestBody PublisherDTO publisherDTO,
                                                  BindingResult bindingResult,
                                                  Principal principal) {
        ResponseEntity<Object> listError = responseErrorValidator.mappedValidatorService(bindingResult);
        if (!ObjectUtils.isEmpty(listError)) return listError;
        Publisher publisher = publisherService.updatePublisher(publisherDTO, principal);
        PublisherDTO publisherDTO1 = publisherFacade.publisherDTO(publisher);
        return new ResponseEntity<>(publisherDTO1, HttpStatus.OK);
    }

    @PostMapping("/staff/books/publisher/delete")
    public ResponseEntity<Object> deletePublisher(@Valid @RequestBody String id,
                                                  Principal principal) {
        String resultDelete = publisherService.deletePublisher(Integer.parseInt(id), principal);
        return new ResponseEntity<>(new MessageResponse(resultDelete), HttpStatus.OK);
    }

}
