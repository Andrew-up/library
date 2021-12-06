package com.netcracker.ageev.library.repository.books;

import com.netcracker.ageev.library.entity.books.AgeLimit;
import com.netcracker.ageev.library.entity.books.CoverCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CoverCodeRepository extends JpaRepository<CoverCode,Long> {

    Optional<CoverCode> findCoverCodeById(Integer id);
}
