package com.netcracker.ageev.library.repository.users;


import com.netcracker.ageev.library.model.users.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users,Long> {

    Optional<Users> findUsersById(Long id);

    Optional<Users> findUsersByEmail(String email);

    Optional<Users> findUsersByPhone(String phone);

    Optional<Users> findUsersByLastname(String surname);

    Users findByEmail(String email);

    Optional<Users> findUserByEmail(String email);

    @Query("select u from Users u left join BasketUser bu on u.id= bu.usersId.id where bu.isRequestCreated is true ")
    List<Users> findAllByBasketUserNotNull();

    List<Users> findAllByOrderById();
}
