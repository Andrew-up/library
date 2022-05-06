package com.netcracker.ageev.library.repository;


import com.netcracker.ageev.library.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    Optional<Image> findByFileName(String name);

    Optional<Image> findById(Long id);

    Optional<Image> findByBooksId(Long id);

    Optional<Image> findByUsersId(Long id);


}
