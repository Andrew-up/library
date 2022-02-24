package com.netcracker.ageev.library.facade;

import com.netcracker.ageev.library.dto.CoverBookDTO;
import com.netcracker.ageev.library.model.books.CoverBook;
import org.springframework.stereotype.Component;

@Component
public class CoverBookFacade {

    public CoverBookDTO coverBookDTO(CoverBook coverBook){
        CoverBookDTO coverBookDTO = new CoverBookDTO();
        coverBookDTO.setCoverBookId(coverBook.getId());
        coverBookDTO.setCoverBookName(coverBook.getName());
        return coverBookDTO;
    }
}
