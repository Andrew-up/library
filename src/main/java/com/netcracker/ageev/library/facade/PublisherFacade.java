package com.netcracker.ageev.library.facade;

import com.netcracker.ageev.library.dto.PublisherDTO;
import com.netcracker.ageev.library.model.books.Publisher;
import org.springframework.stereotype.Component;

@Component
public class PublisherFacade {

    public PublisherDTO publisherDTO(Publisher publisher){
        PublisherDTO publisherDTO = new PublisherDTO();
        publisherDTO.setPublisherId(publisher.getId());
        publisherDTO.setPublisherName(publisher.getName());
        return publisherDTO;
    }
}
