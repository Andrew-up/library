package com.netcracker.ageev.library.repository.users;

import com.netcracker.ageev.library.entity.books.AgeLimit;
import com.netcracker.ageev.library.entity.users.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users,Long> {

    Optional<Users> findUsersById(Integer id);

    Optional<Users> findUsersByEmail(String email);

    Optional<Users> findUsersByPhone(String phone);

    Optional<Users> findUsersByName(String name);

    Optional<Users> findUsersBySurname(String surname);
}
