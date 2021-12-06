package com.netcracker.ageev.library.repository.books;

import com.netcracker.ageev.library.entity.books.Authors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorsRepository extends JpaRepository<Authors,Long> {

    Optional<Authors> findAuthorsById(Integer id);

    Optional<Authors> findAuthorsByFirstname(String firstname);

    Optional<Authors> findAuthorsByLastname(String lastname);


}
