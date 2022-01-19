package com.netcracker.ageev.library.repository.books;

import com.netcracker.ageev.library.model.books.TranslationBooks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TranslationRepository extends JpaRepository<TranslationBooks,Long> {

    List<TranslationBooks> findAllByOrderByTranslationId();
}
