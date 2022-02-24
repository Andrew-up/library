package com.netcracker.ageev.library.repository.books;


import com.netcracker.ageev.library.model.books.CoverBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CoverBookRepository extends JpaRepository<CoverBook,Long> {

    Optional<CoverBook> findCoverBookById(Integer id);

    List<CoverBook> findAllByOrderById();
}
