package com.netcracker.ageev.library.facade;

import com.netcracker.ageev.library.dto.EditionLanguageDTO;
import com.netcracker.ageev.library.model.books.EditionLanguage;
import org.springframework.stereotype.Component;

@Component
public class EditionLanguageFacade {

    public EditionLanguageDTO editionLanguageDTO(EditionLanguage editionLanguage){
        EditionLanguageDTO editionLanguageDTO = new EditionLanguageDTO();
        editionLanguageDTO.setLanguageId(editionLanguage.getLanguageId());
        editionLanguageDTO.setLanguageName(editionLanguage.getLanguageName());
        return editionLanguageDTO;
    }
}
