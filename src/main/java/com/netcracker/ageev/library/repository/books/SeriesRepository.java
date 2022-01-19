package com.netcracker.ageev.library.repository.books;

import com.netcracker.ageev.library.model.books.Series;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeriesRepository extends JpaRepository<Series,Long> {


    Optional<Series> findSeriesBySeriesId(Integer id);
    List<Series> findAllByOrderBySeriesId();
}
