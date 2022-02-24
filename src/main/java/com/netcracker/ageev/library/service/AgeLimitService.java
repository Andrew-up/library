package com.netcracker.ageev.library.service;

import com.netcracker.ageev.library.dto.AgeLimitDTO;
import com.netcracker.ageev.library.model.books.AgeLimit;
import com.netcracker.ageev.library.model.enums.ERole;
import com.netcracker.ageev.library.model.users.Users;
import com.netcracker.ageev.library.repository.books.AgeLimitRepository;
import com.netcracker.ageev.library.repository.users.UsersRepository;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AgeLimitService {


    public static final Logger LOG = LoggerFactory.getLogger(AgeLimitService.class);


    private final AgeLimitRepository ageLimitRepository;
    private final UsersRepository usersRepository;
    private final UsersService usersService;

    @Autowired
    public AgeLimitService(AgeLimitRepository ageLimitRepository, UsersRepository usersRepository, UsersService usersService) {
        this.ageLimitRepository = ageLimitRepository;
        this.usersRepository = usersRepository;
        this.usersService = usersService;
    }

    public List<AgeLimit> getAllAgeLimit() {
        return ageLimitRepository.findAllByOrderById();
    }

    public AgeLimit getAgeLimitById(Integer id) {
        return ageLimitRepository.findAgeLimitById(id).orElseThrow(() -> new NullPointerException("not found"));
    }

    public AgeLimit createAgeLimit(AgeLimitDTO ageLimitDTO, Principal principal) {
        Users users = usersService.getUserByPrincipal(principal);
        List<String> arrayListError = new ArrayList<>(isAgeLimitCorrect(ageLimitDTO));
        AgeLimit ageLimit = new AgeLimit();
        if (!ObjectUtils.isEmpty(arrayListError)) {
            ageLimit.setAge(arrayListError.toString());
            return ageLimit;
        }
        if (usersService.DataAccessToUser(users)) {
            return saveAgeLimit(ageLimitDTO, ageLimit);
        } else {
            ageLimit.setAge("Для пользователя с ролью " + users.getERole() + " добавление невозможно");
            return ageLimit;
        }
    }


    public AgeLimit updateAgeLimit(AgeLimitDTO ageLimitDTO, Principal principal) {
        Users users = usersService.getUserByPrincipal(principal);
        List<String> arrayListError = new ArrayList<>(isAgeLimitCorrect(ageLimitDTO));
        AgeLimit ageLimit = ageLimitRepository.findAgeLimitById(ageLimitDTO.getAgeLimitId()).orElseThrow(() -> new UsernameNotFoundException("Age Limit not found"));
        if (!ObjectUtils.isEmpty(arrayListError)) {
            ageLimit.setAge(arrayListError.toString());
            return ageLimit;
        }
        if (usersService.DataAccessToUser(users)) {
            return saveAgeLimit(ageLimitDTO, ageLimit);
        } else {
            ageLimit.setAge("Для пользователя с ролью " + users.getERole() + " обновление невозможно");
            return ageLimit;
        }
    }


    public String deleteGenre(Integer ageLimitId, Principal principal) {
        Users users = usersService.getUserByPrincipal(principal);
        if (usersService.DataAccessToUser(users)) {
            Optional<AgeLimit> delete = ageLimitRepository.findAgeLimitById(ageLimitId);
            delete.ifPresent(ageLimitRepository::delete);
            return "Возрастное ограничение с id: " + ageLimitId + " удалено";
        } else {
            return "Для пользователя с ролью " + users.getERole() + " удаление невозможно";
        }
    }

    private ArrayList<String> isAgeLimitCorrect(AgeLimitDTO ageLimitDTO) {
        int firstNumber;
        int secondNumber;
        ArrayList<String> listError = new ArrayList<>();
        String regex = "(^[0-9]{1,2}\\+)|(^[0-9]{1,2}-[0-9]{1,2})";
        boolean result = ageLimitDTO.getAgeLimitName().matches(regex);
        if (!result) {
            listError.add("Выражение не прошло проверку по формату записи");
        }
        if (result) {
            String[] words = ageLimitDTO.getAgeLimitName().split("-");
            if (words.length == 2) {
                firstNumber = Integer.parseInt(words[0]);
                secondNumber = Integer.parseInt(words[1]);
                if (firstNumber > secondNumber) {
                    listError.add("Первое число не может быть больше второго!");
                }
                if (firstNumber > 21 || secondNumber > 21) {
                    listError.add("Не может быть возрастного ограничения выше 21 года");
                }
                if (firstNumber == secondNumber) {
                    listError.add("Следует писать: " + firstNumber + "+");
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
            ageLimit.setAge("Ошибка при добавлении в бд, такая запись уже есть");
            return ageLimit;
        }
    }
}
