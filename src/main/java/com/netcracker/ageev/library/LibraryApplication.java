package com.netcracker.ageev.library;


import com.netcracker.ageev.library.exception.DataNotFoundException;
import com.netcracker.ageev.library.service.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication()
@EnableJpaAuditing

public class LibraryApplication {
    private static final Logger LOG = LoggerFactory.getLogger(LibraryApplication.class);
    public static void main(String[] args) {
//        for (int i=0; i<1000;i++){
//            LOG.info("test22");
//        }

        SpringApplication.run(LibraryApplication.class, args);
        throw  new DataNotFoundException("test");

    }

}
