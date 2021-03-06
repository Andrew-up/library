package com.netcracker.ageev.library.service;

import com.netcracker.ageev.library.dto.AgeLimitDTO;
import com.netcracker.ageev.library.exception.DataNotFoundException;
import com.netcracker.ageev.library.model.books.AgeLimit;
import com.netcracker.ageev.library.repository.books.AgeLimitRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AgeLimitService {

    public static final Logger LOG = LoggerFactory.getLogger(AgeLimitService.class);

    private final AgeLimitRepository ageLimitRepository;

    @Autowired
    public AgeLimitService(AgeLimitRepository ageLimitRepository) {
        this.ageLimitRepository = ageLimitRepository;
    }

    public List<AgeLimit> getAllAgeLimit() {
        return ageLimitRepository.findAllByOrderById();
    }

    public AgeLimit getAgeLimitById(Integer id) {
        return ageLimitRepository.findAgeLimitById(id).orElseThrow(() -> new NullPointerException("not found"));
    }

    public AgeLimit createAgeLimit(AgeLimitDTO ageLimitDTO, Principal principal) {

        List<String> arrayListError = new ArrayList<>(isAgeLimitCorrect(ageLimitDTO));
        AgeLimit ageLimit = new AgeLimit();
        if (!ObjectUtils.isEmpty(arrayListError)) {
            ageLimit.setAge(arrayListError.toString());
            return ageLimit;
        }
        return saveAgeLimit(ageLimitDTO, ageLimit);

    }


    public AgeLimit updateAgeLimit(AgeLimitDTO ageLimitDTO, Principal principal) {
        List<String> arrayListError = new ArrayList<>(isAgeLimitCorrect(ageLimitDTO));
        AgeLimit ageLimit = ageLimitRepository.findAgeLimitById(ageLimitDTO.getAgeLimitId()).orElseThrow(() -> new DataNotFoundException("Age Limit not found"));
        if (!ObjectUtils.isEmpty(arrayListError)) {
            ageLimit.setAge(arrayListError.toString());
            return ageLimit;
        }

        return saveAgeLimit(ageLimitDTO, ageLimit);

    }


    public String deleteGenre(Integer ageLimitId, Principal principal) {

        Optional<AgeLimit> delete = ageLimitRepository.findAgeLimitById(ageLimitId);
        delete.ifPresent(ageLimitRepository::delete);
        return "???????????????????? ?????????????????????? ?? id: " + ageLimitId + " ??????????????";
    }

    private ArrayList<String> isAgeLimitCorrect(AgeLimitDTO ageLimitDTO) {
        int firstNumber;
        int secondNumber;
        ArrayList<String> listError = new ArrayList<>();
        String regex = "(^[0-9]{1,2}\\+)|(^[0-9]{1,2}-[0-9]{1,2})";
        boolean result = ageLimitDTO.getAgeLimitName().matches(regex);
        if (!result) {
            LOG.info("The expression did not pass the record format check. isAgeLimitCorrect :" + ageLimitDTO.getAgeLimitName());
            listError.add("?????????????????? ???? ???????????? ???????????????? ???? ?????????????? ????????????");
        }
        if (result) {
            String[] words = ageLimitDTO.getAgeLimitName().split("-");
            if (words.length == 2) {
                firstNumber = Integer.parseInt(words[0]);
                secondNumber = Integer.parseInt(words[1]);
                if (firstNumber > secondNumber) {
                    listError.add("???????????? ?????????? ???? ?????????? ???????? ???????????? ??????????????!");
                }
                if (firstNumber > 21 || secondNumber > 21) {
                    listError.add("???? ?????????? ???????? ?????????????????????? ?????????????????????? ???????? 21 ????????");
                }
                if (firstNumber == secondNumber) {
                    listError.add("?????????????? ????????????: " + firstNumber + "+");
                }
            }
        }

        return listError;
    }

    private AgeLimit saveAgeLimit(AgeLimitDTO ageLimitDTO, AgeLimit ageLimit) {
        try {
            ageLimit.setAge(ageLimitDTO.getAgeLimitName());
            ageLimitRepository.save(ageLimit);
            return ageLimit;
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            ageLimit.setId(-2000);
            LOG.info("Error when adding to the database, such an entry already exists: "+ ageLimitDTO.getAgeLimitName());
            ageLimit.setAge("???????????? ?????? ???????????????????? ?? ????, ?????????? ???????????? ?????? ????????");
            return ageLimit;
        }
    }
}
