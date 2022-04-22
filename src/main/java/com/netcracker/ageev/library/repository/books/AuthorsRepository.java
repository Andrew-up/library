package com.netcracker.ageev.library.repository.books;


import com.netcracker.ageev.library.model.books.Authors;
import com.netcracker.ageev.library.model.books.Books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorsRepository extends JpaRepository<Authors,Long> {

    List<Authors> findAllByOrderById();

    Optional<Authors> findAuthorsById(Integer id);
    Optional<Authors> findAuthorsBySeriesId(Integer id);


    @Query("select a from Authors a where concat(upper(a.lastname), upper(a.firstname),upper(a.patronymic))  like concat('%', upper(?1), '%') order by a.id")
    List<Authors> findAllByAuthors(String authors);

}
