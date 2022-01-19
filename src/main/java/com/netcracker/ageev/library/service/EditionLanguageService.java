package com.netcracker.ageev.library.service;

import com.netcracker.ageev.library.dto.EditionLanguageDTO;
import com.netcracker.ageev.library.dto.TranslationDTO;
import com.netcracker.ageev.library.model.books.EditionLanguage;
import com.netcracker.ageev.library.repository.books.EditionLanguageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class EditionLanguageService {


    public static final Logger LOG = LoggerFactory.getLogger(EditionLanguageService.class);

    private final EditionLanguageRepository editionLanguageRepository;

    @Autowired
    public EditionLanguageService(EditionLanguageRepository editionLanguageRepository) {
        this.editionLanguageRepository = editionLanguageRepository;
    }

    public List<EditionLanguage> getAllLanguage() {
        return editionLanguageRepository.findAllByOrderByLanguageId();
    }


    public EditionLanguage createEditionLanguage(EditionLanguageDTO editionLanguageDTO, Principal principal){
        EditionLanguage editionLanguage = new EditionLanguage();
        ArrayList<String> arrayListError =  isEditionLanguageCorrect(editionLanguageDTO);
        if (!ObjectUtils.isEmpty(arrayListError)){
            editionLanguage.setLanguageName(arrayListError.toString());
            return editionLanguage;
        }
        editionLanguage.setLanguageName(editionLanguageDTO.getLanguageName());
        return editionLanguageRepository.save(editionLanguage);
    }

    private ArrayList<String> isEditionLanguageCorrect(EditionLanguageDTO editionLanguageDTO){
        ArrayList<String> listError = new ArrayList<>();
        if(editionLanguageDTO.getLanguageName().isEmpty()){
            listError.add("Язык издания не корректен");
        }
        return listError;
    }

}


