package com.netcracker.ageev.library.service;

import com.netcracker.ageev.library.dto.PublisherDTO;
import com.netcracker.ageev.library.exception.DataNotFoundException;
import com.netcracker.ageev.library.model.books.Publisher;
import com.netcracker.ageev.library.model.users.Users;
import com.netcracker.ageev.library.repository.books.PublisherRepository;
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

@Service
public class PublisherService {

    public static final Logger LOG = LoggerFactory.getLogger(AgeLimitService.class);


    private final PublisherRepository publisherRepository;
    private final UsersRepository usersRepository;
    private final UsersService usersService;

    @Autowired
    public PublisherService(PublisherRepository publisherRepository, UsersRepository usersRepository, UsersService usersService) {
        this.publisherRepository = publisherRepository;
        this.usersRepository = usersRepository;
        this.usersService = usersService;
    }

    public List<Publisher> getAllPublisher() {
        return publisherRepository.findAllByOrderById();
    }

    public Publisher createPublisher(PublisherDTO publisherDTO, Principal principal) {

        Publisher publisher = new Publisher();
        ArrayList<String> arrayListError = isPublisherCorrect(publisherDTO);
        if (!ObjectUtils.isEmpty(arrayListError)) {
            publisher.setName(arrayListError.toString());
            return publisher;
        }

        return savePublisher(publisherDTO, publisher);

    }

    private ArrayList<String> isPublisherCorrect(PublisherDTO publisherDTO) {
        ArrayList<String> listError = new ArrayList<>();
        String regex = "^([а-яА-Яa-zA-Z]+).*$";
        boolean result = publisherDTO.getPublisherName().matches(regex);
        if (!result) {
            LOG.error("Error when adding to the database, such an entry already exists. isPublisherCorrect :" + publisherDTO.getPublisherName());
            listError.add("Выражение не прошло проверку по формату записи");
        }
        return listError;
    }

    public Publisher getPublisherById(Integer id) {
        try {
            return publisherRepository.findPublisherById(id).orElseThrow(() -> new DataNotFoundException("not found. getPublisherById: "+ id));
        } catch (DataNotFoundException e) {
            return null;
        }
    }

    public Publisher updatePublisher(PublisherDTO publisherDTO, Principal principal) {

        ArrayList<String> arrayListError = isPublisherCorrect(publisherDTO);
        Publisher publisher = publisherRepository.findPublisherById(publisherDTO.getPublisherId()).orElseThrow(() -> new DataNotFoundException("not found. updatePublisher: "+ publisherDTO.getPublisherId()));
        if (!ObjectUtils.isEmpty(arrayListError)) {
            publisher.setName(arrayListError.toString());
            return publisher;
        }
        return savePublisher(publisherDTO, publisher);

    }

    private Publisher savePublisher(PublisherDTO publisherDTO, Publisher publisher) {
        try {
            publisher.setName(publisherDTO.getPublisherName());
            publisherRepository.save(publisher);
            return publisher;
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            publisher.setId(-2000);
            LOG.info("Error when adding to the database, such an entry already exists. publisherDTO: "+ publisherDTO.getPublisherName());
            publisher.setName("Ошибка при добавлении в бд, такая запись уже есть");
            return publisher;
        }
    }

    public String deletePublisher(Integer publisherId, Principal principal) {
        Optional<Publisher> delete = publisherRepository.findPublisherById(publisherId);
        delete.ifPresent(publisherRepository::delete);
        return "Тип обложки с id: " + publisherId + " удалено";

    }

}
