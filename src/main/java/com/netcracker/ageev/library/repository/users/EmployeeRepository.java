package com.netcracker.ageev.library.repository.users;

import com.netcracker.ageev.library.entity.books.AgeLimit;
import com.netcracker.ageev.library.entity.users.Employee;
import com.netcracker.ageev.library.entity.users.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {

    Optional<Employee> findEmployeeById(Integer id);

}
