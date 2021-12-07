package com.netcracker.ageev.library.repository.books;


import com.netcracker.ageev.library.model.books.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PublisherRepository  extends JpaRepository<Publisher,Long>  {

    Optional<Publisher> findPublisherById(Integer id);
}
