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

    @Autowired
    private final UsersRepository usersRepository;

    @Autowired
    private final UsersService usersService;

    @Autowired
    public AgeLimitService(AgeLimitRepository ageLimitRepository, UsersRepository usersRepository, UsersService usersService) {
        this.ageLimitRepository = ageLimitRepository;
        this.usersRepository = usersRepository;
        this.usersService = usersService;
    }

    public AgeLimit getAgeLimitById(Integer id) {
        return ageLimitRepository.findAgeLimitById(id).orElseThrow(() -> new NullPointerException("not found"));
    }

    public AgeLimit createAgeLimit(AgeLimitDTO ageLimitDTO, Principal principal) {
        Users users = usersService.getUserByPrincipal(principal);
        List<String> arrayListError = new ArrayList<>(isAgeLimitCorrect(ageLimitDTO));
        AgeLimit ageLimit = new AgeLimit();
        if (users.getERole().equals(ERole.ROLE_WORKER) || (users.getERole().equals(ERole.ROLE_ADMIN))) {
            if (!ObjectUtils.isEmpty(arrayListError)) {
                ageLimit.setAge(arrayListError.toString());
                return ageLimit;
            }
            ageLimit.setAge(ageLimitDTO.getAgeLimitName());
            LOG.info("Create Age Limit for user:{}", users.getEmail());
            return ageLimitRepository.save(ageLimit);
        }
         else {
            ageLimit.setAge("Для пользователя с ролью " + users.getERole() + " добавление невозможно");
            return ageLimit;
        }
    }

    public List<AgeLimit> getAllAgeLimit() {
        return ageLimitRepository.findAllByOrderById();
    }

    public AgeLimit updateAgeLimit(AgeLimitDTO ageLimitDTO, Principal principal) {
        Users users = usersService.getUserByPrincipal(principal);
        List<String> arrayListError = new ArrayList<>(isAgeLimitCorrect(ageLimitDTO));
        AgeLimit ageLimit = ageLimitRepository.findAgeLimitById(ageLimitDTO.getAgeLimitId()).orElseThrow(() -> new UsernameNotFoundException("Age Limit not found"));
        if (users.getERole().equals(ERole.ROLE_WORKER) || (users.getERole().equals(ERole.ROLE_ADMIN))) {
            if (!ObjectUtils.isEmpty(arrayListError)) {
                ageLimit.setAge(arrayListError.toString());
                return ageLimit;
            }
            ageLimit.setAge(ageLimitDTO.getAgeLimitName());
            return ageLimitRepository.save(ageLimit);
        } else {
            ageLimit.setAge("Для пользователя с ролью " + users.getERole() + " обновление невозможно");
            return ageLimit;
        }
    }

    public String deleteGenre(Integer ageLimitId, Principal principal) {
        Users users = usersService.getUserByPrincipal(principal);
        if (users.getERole().equals(ERole.ROLE_WORKER) || (users.getERole().equals(ERole.ROLE_ADMIN))) {
            Optional<AgeLimit> delete = ageLimitRepository.findAgeLimitById(ageLimitId);
            delete.ifPresent(ageLimitRepository::delete);
            return "Возрастное ограничение с id: " + ageLimitId + " удалено";
        } else {
            return "Для пользователя с ролью " + users.getERole() + " удаление невозможно";
        }
    }

    private ArrayList<String> isAgeLimitCorrect(AgeLimitDTO ageLimitDTO) {
        ArrayList<String> listError = new ArrayList<>();
        String regex = "(^[0-9]{1,2}\\+)|(^[0-9]{1,2}-[0-9]{1,2})";
        boolean result = ageLimitDTO.getAgeLimitName().matches(regex);
        if(!result){
            listError.add("Выражение не прошло проверку по формату записи");
          
        }
        return listError;
    }
}
