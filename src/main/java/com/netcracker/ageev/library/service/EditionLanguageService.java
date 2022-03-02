package com.netcracker.ageev.library.service;

import com.netcracker.ageev.library.dto.EditionLanguageDTO;
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
    public EditionLanguageService(EditionLanguageRepository editionLanguageRepository, UsersService usersService) {
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
        Users users = usersService.getUserByPrincipal(principal);
        List<String> arrayListError = new ArrayList<>(isEditionLanguageCorrect(editionLanguageDTO));
        EditionLanguage editionLanguage = editionLanguageRepository.findEditionLanguageByLanguageId(editionLanguageDTO.getLanguageId()).orElseThrow(() -> new UsernameNotFoundException("Translation not found"));
        if (!ObjectUtils.isEmpty(arrayListError)) {
            editionLanguage.setLanguageName(arrayListError.toString());
            return editionLanguage;
        }
        if (usersService.DataAccessToUser(users)) {
            return saveEditionLanguageDB(editionLanguageDTO, editionLanguage);
        } else {
            editionLanguage.setLanguageName("Для пользователя с ролью " + users.getERole() + " обновление невозможно");
            return editionLanguage;
        }
    }

    private EditionLanguage saveEditionLanguageDB(EditionLanguageDTO editionLanguageDTO, EditionLanguage editionLanguage) {
        try {
            editionLanguage.setLanguageName(editionLanguageDTO.getLanguageName());
            editionLanguageRepository.save(editionLanguage);
            return editionLanguage;
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            editionLanguage.setLanguageId(-2000);
            editionLanguage.setLanguageName("Ошибка при добавлении в бд, такая запись уже есть");
            return editionLanguage;
        }
    }

    public String deleteEditionLanguage(Integer editionLanguageId, Principal principal) {
        Users users = usersService.getUserByPrincipal(principal);
        if (usersService.DataAccessToUser(users)) {
            Optional<EditionLanguage> delete = editionLanguageRepository.findEditionLanguageByLanguageId(editionLanguageId);
            delete.ifPresent(editionLanguageRepository::delete);
            return "Язык издания с id: " + editionLanguageId + " удален";
        } else {
            return "Для пользователя с ролью " + users.getERole() + " удаление невозможно";
        }
    }

    private ArrayList<String> isEditionLanguageCorrect(EditionLanguageDTO editionLanguageDTO) {
        ArrayList<String> listError = new ArrayList<>();
        String regex = "^(([a-zA-Zа-яА-Я]*\\s*){2,3})$";
        boolean result = editionLanguageDTO.getLanguageName().matches(regex);
        System.out.println("result:"+result);
        if (!result) {
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


