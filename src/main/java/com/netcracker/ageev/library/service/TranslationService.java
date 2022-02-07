package com.netcracker.ageev.library.service;

import com.netcracker.ageev.library.dto.CoverCodeDTO;
import com.netcracker.ageev.library.dto.TranslationDTO;
import com.netcracker.ageev.library.model.books.CoverCode;
import com.netcracker.ageev.library.model.books.TranslationBooks;
import com.netcracker.ageev.library.repository.books.TranslationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class TranslationService {


    public static final Logger LOG = LoggerFactory.getLogger(TranslationService.class);

    public TranslationService(TranslationRepository translationRepository) {
        this.translationRepository = translationRepository;
    }

    private final TranslationRepository translationRepository;
    public List<TranslationBooks> getAllTranslation(){
        return translationRepository.findAllByOrderByTranslationId();
    }

    public TranslationBooks createTranslation(TranslationDTO translationDTO, Principal principal){
        TranslationBooks translationBooks = new TranslationBooks();
        ArrayList<String> arrayListError =  isTranslationCorrect(translationDTO);
        if (!ObjectUtils.isEmpty(arrayListError)){
            translationBooks.setTranslationName(arrayListError.toString());
            return translationBooks;
        }
        translationBooks.setTranslationName(translationDTO.getTranslationName());
        return translationRepository.save(translationBooks);
    }

    private ArrayList<String> isTranslationCorrect(TranslationDTO translationDTO){
        ArrayList<String> listError = new ArrayList<>();
        if(translationDTO.getTranslationName().isEmpty()){
            listError.add("Автор перевода не корректен");
        }
        return listError;
    }

    public TranslationBooks getTranslationById(Integer id){
        try {
            return  translationRepository.findTranslationBooksByTranslationId(id).orElseThrow(() ->  new NullPointerException("not found"));
        }
        catch (NullPointerException e){
            return  null;
        }
    }

}
