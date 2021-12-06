package com.netcracker.ageev.library.repository.books;

import com.netcracker.ageev.library.entity.books.AgeLimit;
import com.netcracker.ageev.library.entity.books.BookRent;
import com.netcracker.ageev.library.entity.books.Books;
import com.netcracker.ageev.library.entity.users.Employee;
import com.netcracker.ageev.library.entity.users.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRentRepository extends JpaRepository<BookRent,Long> {

    Optional<BookRent> findBookRentById(Integer id);
    Optional<BookRent> findBookRentByBooksId(Books bookId);
    Optional<BookRent> findBookRentByEmployeeId(Employee employeeId);
    Optional<BookRent> findBookRentByUsersId(Users usersId);

    List<BookRent> findAllByOrderByDateIssue();

}
