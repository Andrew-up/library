package com.netcracker.ageev.library.repository.books;

import com.netcracker.ageev.library.model.books.BookGenres;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookGenresRepository extends JpaRepository<BookGenres,Long> {

    List<BookGenres> findAllByOrderByBookGenresId();
    Optional<BookGenres> findBookGenresByBookGenresId(Integer id);


}
