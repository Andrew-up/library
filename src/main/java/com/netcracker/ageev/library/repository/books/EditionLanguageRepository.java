package com.netcracker.ageev.library.repository.books;


import com.netcracker.ageev.library.model.books.EditionLanguage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface EditionLanguageRepository extends JpaRepository<EditionLanguage,Long> {

    Optional<EditionLanguage> findEditionLanguageByLanguageId(Integer id);


    List<EditionLanguage> findAllByOrderByLanguageId();

}

