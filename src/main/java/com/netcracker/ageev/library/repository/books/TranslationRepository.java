package com.netcracker.ageev.library.repository.books;

import com.netcracker.ageev.library.model.books.BookGenres;
import com.netcracker.ageev.library.model.books.TranslationBooks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TranslationRepository extends JpaRepository<TranslationBooks,Long> {

    List<TranslationBooks> findAllByOrderByTranslationId();
    Optional<TranslationBooks> findTranslationBooksByTranslationId(Integer id);
}
