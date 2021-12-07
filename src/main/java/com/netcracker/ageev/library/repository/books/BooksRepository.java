package com.netcracker.ageev.library.repository.books;


import com.netcracker.ageev.library.model.books.Books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface BooksRepository extends JpaRepository<Books,Long> {

    Optional<Books> findBooksByBookTitle(String title);

    Optional<Books> findBooksByISBN(String ISBN);

    Optional<Books> findBooksById(Long id);

    Optional<Books> findBooksBySeries(String series);
}
