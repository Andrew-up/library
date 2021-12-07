package com.netcracker.ageev.library.service;

import com.netcracker.ageev.library.dto.AgeLimitDTO;
import com.netcracker.ageev.library.model.books.AgeLimit;
import com.netcracker.ageev.library.repository.books.AgeLimitRepository;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgeLimitService {


    public static final Logger LOG = LoggerFactory.getLogger(AgeLimitService.class);


    private final AgeLimitRepository ageLimitRepository;

    @Autowired
    public AgeLimitService(AgeLimitRepository ageLimitRepository) {
        this.ageLimitRepository = ageLimitRepository;
    }

    public AgeLimit getAgeLimitById(Integer id){
        return ageLimitRepository.findAgeLimitById(id).orElseThrow(() -> new NullPointerException("not found"));
    }

    public AgeLimit saveAgeLimit(AgeLimitDTO ageLimitDTO){
        AgeLimit ageLimit = new AgeLimit();
        ageLimit.setAge(ageLimit.getAge());
        return ageLimitRepository.save(ageLimit);
    }

    public AgeLimit createAgeLimit(AgeLimitDTO ageLimitDTO){
        AgeLimit ageLimit = new AgeLimit();
        ageLimit.setAge(ageLimitDTO.getAgeLimit());
        return ageLimitRepository.save(ageLimit);
    }
}
