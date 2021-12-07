package com.netcracker.ageev.library.facade;

import com.netcracker.ageev.library.dto.AgeLimitDTO;
import com.netcracker.ageev.library.model.books.AgeLimit;
import com.netcracker.ageev.library.repository.books.AgeLimitRepository;
import com.netcracker.ageev.library.service.PriceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class AgeLimitFacade {

    public AgeLimitDTO ageLimitDTO(AgeLimit ageLimit){
        AgeLimitDTO ageLimitDTO = new AgeLimitDTO();
        ageLimitDTO.setId(ageLimit.getId());
        ageLimitDTO.setAgeLimit(ageLimit.getAge());
        return ageLimitDTO;
    }
}
