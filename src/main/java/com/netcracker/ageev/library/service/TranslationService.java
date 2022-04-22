package com.netcracker.ageev.library.service;

import com.netcracker.ageev.library.dto.TranslationDTO;
import com.netcracker.ageev.library.model.books.TranslationBooks;
import com.netcracker.ageev.library.repository.books.TranslationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TranslationService {


    public static final Logger LOG = LoggerFactory.getLogger(TranslationService.class);
    private final TranslationRepository translationRepository;
    private final UsersService usersService;

    public TranslationService(TranslationRepository translationRepository, UsersService usersService) {
        this.translationRepository = translationRepository;
        this.usersService = usersService;
    }


    public List<TranslationBooks> getAllTranslation() {
        return translationRepository.findAllByOrderByTranslationId();
    }

    public TranslationBooks createTranslation(TranslationDTO translationDTO, Principal principal) {
        TranslationBooks translationBooks = new TranslationBooks();
        ArrayList<String> arrayListError = isTranslationCorrect(translationDTO);
        if (!ObjectUtils.isEmpty(arrayListError)) {
            translationBooks.setTranslationName(arrayListError.toString());
            return translationBooks;
        }
        translationBooks.setTranslationName(translationDTO.getTranslationName());
        return translationRepository.save(translationBooks);
    }

    public TranslationBooks getTranslationById(Integer id) {
        try {
            return translationRepository.findTranslationBooksByTranslationId(id).orElseThrow(() -> new NullPointerException("not found"));
        } catch (NullPointerException e) {
            return null;
        }
    }


    public TranslationBooks updateTranslationBooks(TranslationDTO translationDTO, Principal principal) {

        List<String> arrayListError = new ArrayList<>(isTranslationCorrect(translationDTO));
        TranslationBooks translationBooks = translationRepository.findTranslationBooksByTranslationId(translationDTO.getTranslationId()).orElseThrow(() -> new UsernameNotFoundException("Translation not found"));
        if (!ObjectUtils.isEmpty(arrayListError)) {
            translationBooks.setTranslationName(arrayListError.toString());
            return translationBooks;
        }

        return saveTranslationDB(translationDTO, translationBooks);

    }

    public String deleteTranslation(Integer translationId, Principal principal) {

        Optional<TranslationBooks> delete = translationRepository.findTranslationBooksByTranslationId(translationId);
        delete.ifPresent(translationRepository::delete);
        return "Перевод с id: " + translationId + " удален";

    }

    private TranslationBooks saveTranslationDB(TranslationDTO translationDTO, TranslationBooks translationBooks) {
        try {
            translationBooks.setTranslationName(translationDTO.getTranslationName());
            translationRepository.save(translationBooks);
            return translationBooks;
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            translationBooks.setTranslationId(-2000);
            translationBooks.setTranslationName("Ошибка при добавлении в бд, такая запись уже есть");
            return translationBooks;
        }
    }

    private ArrayList<String> isTranslationCorrect(TranslationDTO translationDTO) {
        ArrayList<String> listError = new ArrayList<>();
        System.out.println(translationDTO.getTranslationName());
        String regex = "^(([a-zA-Zа-яА-Я]*\\s*){2,3})$";

        boolean result = translationDTO.getTranslationName().matches(regex);
        System.out.println("result:" + result);

        if (!result || translationDTO.getTranslationName().equals("")) {
            listError.add("Выражение не прошло проверку по формату записи");
        }

        return listError;
    }


}
