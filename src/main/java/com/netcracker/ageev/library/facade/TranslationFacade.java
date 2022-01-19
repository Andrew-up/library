package com.netcracker.ageev.library.facade;

import com.netcracker.ageev.library.dto.TranslationDTO;
import com.netcracker.ageev.library.model.books.TranslationBooks;
import org.springframework.stereotype.Component;

@Component
public class TranslationFacade {

    public TranslationDTO translationDTO(TranslationBooks translationBooks){

        TranslationDTO translationDTO = new TranslationDTO();
        translationDTO.setTranslationId(translationBooks.getTranslationId());
        translationDTO.setTranslationName(translationBooks.getTranslationName());
        return translationDTO;

    }
}
