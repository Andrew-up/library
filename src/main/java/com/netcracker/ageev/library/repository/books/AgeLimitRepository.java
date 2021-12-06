package com.netcracker.ageev.library.repository.books;

import com.netcracker.ageev.library.entity.books.AgeLimit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface AgeLimitRepository extends JpaRepository<AgeLimit,Long> {

    Optional<AgeLimit> findAgeLimitById(Integer id);
}
