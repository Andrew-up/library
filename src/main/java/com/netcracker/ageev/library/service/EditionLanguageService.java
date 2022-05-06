package com.netcracker.ageev.library.service;

import com.netcracker.ageev.library.dto.EditionLanguageDTO;
import com.netcracker.ageev.library.exception.DataNotFoundException;
import com.netcracker.ageev.library.model.books.EditionLanguage;
import com.netcracker.ageev.library.model.users.Users;
import com.netcracker.ageev.library.repository.books.EditionLanguageRepository;
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
public class EditionLanguageService {


    public static final Logger LOG = LoggerFactory.getLogger(EditionLanguageService.class);

    private final EditionLanguageRepository editionLanguageRepository;
    private final UsersService usersService;

    @Autowired
    public EditionLanguageService(EditionLanguageRepository editionLanguageRepository,
                                  UsersService usersService) {
        this.editionLanguageRepository = editionLanguageRepository;
        this.usersService = usersService;
    }

    public List<EditionLanguage> getAllLanguage() {
        return editionLanguageRepository.findAllByOrderByLanguageId();
    }


    public EditionLanguage createEditionLanguage(EditionLanguageDTO editionLanguageDTO, Principal principal) {
        EditionLanguage editionLanguage = new EditionLanguage();
        ArrayList<String> arrayListError = isEditionLanguageCorrect(editionLanguageDTO);
        if (!ObjectUtils.isEmpty(arrayListError)) {
            editionLanguage.setLanguageName(arrayListError.toString());
            return editionLanguage;
        }
        editionLanguage.setLanguageName(editionLanguageDTO.getLanguageName());
        return editionLanguageRepository.save(editionLanguage);
    }

    public EditionLanguage updateEditionLanguage(EditionLanguageDTO editionLanguageDTO, Principal principal) {

        List<String> arrayListError = new ArrayList<>(isEditionLanguageCorrect(editionLanguageDTO));
        EditionLanguage editionLanguage = editionLanguageRepository
                .findEditionLanguageByLanguageId(editionLanguageDTO.getLanguageId())
                .orElseThrow(() -> new DataNotFoundException("Translation not found. updateEditionLanguage: "+editionLanguageDTO.getLanguageId()));
        if (!ObjectUtils.isEmpty(arrayListError)) {
            editionLanguage.setLanguageName(arrayListError.toString());
            return editionLanguage;
        }

            return saveEditionLanguageDB(editionLanguageDTO, editionLanguage);

    }

    private EditionLanguage saveEditionLanguageDB(EditionLanguageDTO editionLanguageDTO, EditionLanguage editionLanguage) {
        try {
            editionLanguage.setLanguageName(editionLanguageDTO.getLanguageName());
            editionLanguageRepository.save(editionLanguage);
            return editionLanguage;
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            editionLanguage.setLanguageId(-2000);
            LOG.info("Error when adding to the database, such an entry already exists. saveEditionLanguageDB :"+ editionLanguageDTO.getLanguageName());
            editionLanguage.setLanguageName("Ошибка при добавлении в бд, такая запись уже есть");
            return editionLanguage;
        }
    }

    public String deleteEditionLanguage(Integer editionLanguageId, Principal principal) {


            Optional<EditionLanguage> delete = editionLanguageRepository.findEditionLanguageByLanguageId(editionLanguageId);
            delete.ifPresent(editionLanguageRepository::delete);
            return "Язык издания с id: " + editionLanguageId + " удален";

    }

    private ArrayList<String> isEditionLanguageCorrect(EditionLanguageDTO editionLanguageDTO) {
        ArrayList<String> listError = new ArrayList<>();
        String regex = "^(([a-zA-Zа-яА-Я]+\\s*){2,3})$";
        boolean result = editionLanguageDTO.getLanguageName().matches(regex);
        if (!result) {
            LOG.info("The expression did not pass the record format check. isEditionLanguageCorrect: "+ editionLanguageDTO.getLanguageName());
            listError.add("Выражение не прошло проверку по формату записи");
        }
        return listError;
    }


    public EditionLanguage getEditionLanguageById(Integer id) {
        try {
            return editionLanguageRepository.findEditionLanguageByLanguageId(id).orElseThrow(() -> new NullPointerException("not found"));
        } catch (NullPointerException e) {
            return null;
        }
    }


}


