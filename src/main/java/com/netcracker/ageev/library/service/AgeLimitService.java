package com.netcracker.ageev.library.service;

import com.netcracker.ageev.library.dto.AgeLimitDTO;
import com.netcracker.ageev.library.model.books.AgeLimit;
import com.netcracker.ageev.library.model.users.Users;
import com.netcracker.ageev.library.repository.books.AgeLimitRepository;
import com.netcracker.ageev.library.repository.users.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class AgeLimitService {


    public static final Logger LOG = LoggerFactory.getLogger(AgeLimitService.class);


    private final AgeLimitRepository ageLimitRepository;

    @Autowired
    private final UsersRepository usersRepository;

    @Autowired
    public AgeLimitService(AgeLimitRepository ageLimitRepository, UsersRepository usersRepository) {
        this.ageLimitRepository = ageLimitRepository;
        this.usersRepository = usersRepository;
    }

    public AgeLimit getAgeLimitById(Integer id){
        return ageLimitRepository.findAgeLimitById(id).orElseThrow(() -> new NullPointerException("not found"));
    }

    public AgeLimit saveAgeLimit(AgeLimitDTO ageLimitDTO){
        AgeLimit ageLimit = new AgeLimit();
        ageLimit.setAge(ageLimit.getAge());
        return ageLimitRepository.save(ageLimit);
    }

    public AgeLimit createAgeLimit(AgeLimitDTO ageLimitDTO,Principal principal){
        Users users = getUserByPrincipal(principal);
        AgeLimit ageLimit = new AgeLimit();
        ageLimit.setAge(ageLimitDTO.getAgeLimitName());
//        ageLimit.setCreated(ageLimit.getCreated());
//        ageLimit.setUpdated(ageLimitDTO.getUpdated());
        LOG.info("Create Age Limit for user:{}", users.getEmail());
        return ageLimitRepository.save(ageLimit);
    }

    public Users getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return usersRepository.findUsersByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username" + username));
    }

    public List<AgeLimit> getAllAgeLimit(){
        return ageLimitRepository.findAllByOrderByAge();
    }
}
