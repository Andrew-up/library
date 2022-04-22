package com.netcracker.ageev.library.repository.books;


import com.netcracker.ageev.library.model.books.Authors;
import com.netcracker.ageev.library.model.books.Books;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface BooksRepository extends JpaRepository<Books, Long> {

//    Optional<Books> findBooksByBookTitle(String title);

    Optional<Books> findBooksByISBN(String ISBN);

    Optional<Books> findBooksById(Long id);

    List<Books> findAllByAuthors(Authors authors);

//    Optional<Books> findBooksBySeries(Series series);


    List<Books> findAllByOrderById();

    List<Books> findBooksByBookTitleContainingIgnoreCase(String bookTitle);


    @Query("select b from Books b where upper(b.bookTitle) like concat('%', upper(?1), '%') order by b.id")
    List<Books> findAllByBookTitle(String bookTitle);

    @Query("select b from Books b where concat(upper(b.authors.firstname), upper(b.authors.lastname),upper(b.authors.patronymic))  like concat('%', upper(?1), '%') order by b.id")
    List<Books> findAllByAuthors(String authors);

    @Query("select b from Books b where upper(b.genreCode.genre) like concat('%', upper(?1), '%') order by b.id")
    List<Books> findAllByGenres(String genres);

    @Query("select b from Books b where upper(b.publisherId.name) like concat('%', upper(?1), '%') order by b.id")
    List<Books> findAllByPublisher(String publisher);

    @Query("select b from Books b where upper(b.series.seriesName) like concat('%', upper(?1), '%') order by b.id")
    List<Books> findAllBySeries(String series);

    Page<Books> findAllByOrderById(Pageable pageable);

    Page<Books> findAllByOrderByIdDesc(Pageable pageable);

    @Query("select b from Books b join BookRent br on b = br.booksId group by b.id order by count(br.booksId) desc")
    Page<Books> findFrequentRent(Pageable pageable);

}
