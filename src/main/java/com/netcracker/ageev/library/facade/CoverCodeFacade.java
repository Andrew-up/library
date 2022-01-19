package com.netcracker.ageev.library.facade;

import com.netcracker.ageev.library.dto.CoverCodeDTO;
import com.netcracker.ageev.library.model.books.CoverCode;
import org.springframework.stereotype.Component;

@Component
public class CoverCodeFacade {

    public CoverCodeDTO coverCodeDTO(CoverCode coverCode){
        CoverCodeDTO coverCodeDTO = new CoverCodeDTO();
        coverCodeDTO.setCoverCodeId(coverCode.getId());
        coverCodeDTO.setCoverCodeName(coverCode.getName());
        return coverCodeDTO;
    }
}
