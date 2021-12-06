package com.netcracker.ageev.library.repository.books;

import com.netcracker.ageev.library.entity.books.AgeLimit;
import com.netcracker.ageev.library.entity.books.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PriceRepository extends JpaRepository<Price,Long> {

    Optional<Price> findPriceById(Integer id);

}
