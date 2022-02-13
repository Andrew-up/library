package com.netcracker.ageev.library.service;

import com.netcracker.ageev.library.dto.BookGenresDTO;
import com.netcracker.ageev.library.dto.CoverCodeDTO;
import com.netcracker.ageev.library.model.books.CoverCode;
import com.netcracker.ageev.library.model.books.Publisher;
import com.netcracker.ageev.library.repository.books.CoverCodeRepository;
import com.netcracker.ageev.library.repository.users.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CoverCodeService {

    public static final Logger LOG = LoggerFactory.getLogger(CoverCode.class);

    private final CoverCodeRepository coverCodeRepository;
    private final UsersRepository usersRepository;

    public CoverCodeService(CoverCodeRepository coverCodeRepository, UsersRepository usersRepository) {
        this.coverCodeRepository = coverCodeRepository;
        this.usersRepository = usersRepository;
    }

    public List<CoverCode> getAllCoverCode(){
        return coverCodeRepository.findAllByOrderById();
    }

    public CoverCode createCoverCode(CoverCodeDTO coverCodeDTO, Principal principal){
        CoverCode coverCode = new CoverCode();
        ArrayList<String> arrayListError =  isCoverCodeCorrect(coverCodeDTO);
        if (!ObjectUtils.isEmpty(arrayListError)){
            coverCode.setName(arrayListError.toString());
            return coverCode;
        }
        coverCode.setName(coverCodeDTO.getCoverCodeName());
        return coverCodeRepository.save(coverCode);
    }

    private ArrayList<String> isCoverCodeCorrect(CoverCodeDTO coverCodeDTO){
        ArrayList<String> listError = new ArrayList<>();
        if(coverCodeDTO.getCoverCodeName().isEmpty()){
            listError.add("Тип обложки не корректен");
        }
        return listError;
    }

    public CoverCode getCoverCodeById(Integer id){
        try {
            return  coverCodeRepository.findCoverCodeById(id).orElseThrow(() ->  new NullPointerException("not found"));
        }
        catch (NullPointerException e){
            return  null;
        }
    }
}
