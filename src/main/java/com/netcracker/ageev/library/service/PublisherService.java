package com.netcracker.ageev.library.service;

import com.netcracker.ageev.library.dto.PublisherDTO;
import com.netcracker.ageev.library.model.books.Publisher;
import com.netcracker.ageev.library.model.books.TranslationBooks;
import com.netcracker.ageev.library.repository.books.PublisherRepository;
import com.netcracker.ageev.library.repository.users.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class PublisherService {

    public static final Logger LOG = LoggerFactory.getLogger(AgeLimitService.class);


    private final PublisherRepository publisherRepository;
    private final UsersRepository usersRepository;

    @Autowired
    public PublisherService(PublisherRepository publisherRepository, UsersRepository usersRepository) {
        this.publisherRepository = publisherRepository;
        this.usersRepository = usersRepository;
    }

    public List<Publisher> getAllPublisher(){
        return publisherRepository.findAllByOrderById();
    }

    public Publisher createPublisher(PublisherDTO publisherDTO, Principal principal){
        Publisher publisher = new Publisher();
        ArrayList<String> arrayListError = isPublisherCorrect(publisherDTO);
        if(!ObjectUtils.isEmpty(arrayListError)){
            publisher.setName(arrayListError.toString());
            return publisher;
        }
        publisher.setName(publisherDTO.getPublisherName());
        return publisherRepository.save(publisher);
    }

    private ArrayList<String> isPublisherCorrect(PublisherDTO publisherDTO){
        ArrayList<String> listError = new ArrayList<>();
        if(publisherDTO.getPublisherName().isEmpty()){
            listError.add("Имя жанра не корректно");
        }
        return listError;
    }

    public Publisher getPublisherById(Integer id){
        try {
            return  publisherRepository.findPublisherById(id).orElseThrow(() ->  new NullPointerException("not found"));
        }
        catch (NullPointerException e){
            return  null;
        }
    }

}
