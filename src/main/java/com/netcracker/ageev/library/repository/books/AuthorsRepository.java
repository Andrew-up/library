package com.netcracker.ageev.library.repository.books;


import com.netcracker.ageev.library.model.books.Authors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorsRepository extends JpaRepository<Authors,Long> {


    List<Authors> findAllByOrderById();

}
