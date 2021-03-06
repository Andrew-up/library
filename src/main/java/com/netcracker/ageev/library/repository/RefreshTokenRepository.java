package com.netcracker.ageev.library.repository;

import com.netcracker.ageev.library.model.RefreshToken;
import com.netcracker.ageev.library.model.users.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository <RefreshToken,Long> {

    @Override
    Optional<RefreshToken> findById(Long id);

    Optional<RefreshToken> findByToken(String token);


    Optional<RefreshToken> findByUserId(Long userId);


    int deleteByUser(Users users);
}
