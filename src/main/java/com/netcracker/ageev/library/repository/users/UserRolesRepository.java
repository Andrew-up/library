package com.netcracker.ageev.library.repository.users;


import com.netcracker.ageev.library.model.users.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRolesRepository extends JpaRepository<UserRoles,Long> {

    Optional<UserRoles> findUserRoleById(Integer id);
}
