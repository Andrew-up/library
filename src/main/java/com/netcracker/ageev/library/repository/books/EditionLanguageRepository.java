package com.netcracker.ageev.library.repository.books;

import com.netcracker.ageev.library.entity.books.AgeLimit;
import com.netcracker.ageev.library.entity.books.EditionLanguage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface EditionLanguageRepository extends JpaRepository<EditionLanguage,Long> {

    Optional<EditionLanguage> findEditionLanguageById(Integer id);

}
