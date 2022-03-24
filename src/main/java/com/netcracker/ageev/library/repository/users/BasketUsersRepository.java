package com.netcracker.ageev.library.repository.users;

import com.netcracker.ageev.library.model.users.BasketUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BasketUsersRepository  extends JpaRepository<BasketUser,Long> {

    List<BasketUser> findAll ();



    List<BasketUser> findAllByUsersId(Long id);

    Optional<BasketUser> findBasketUserByBasketUserId(Long basketUserId);

    List<BasketUser> findBasketUserByUsersIdAndIsTheBasketIsFalseAndIsRequestCreatedIsTrue(Long basketUserId);

    List<BasketUser> findBasketUserByUsersIdAndIsTheBasketIsTrueAndIsRequestCreatedIsFalse(Long basketUserId);

    List<BasketUser> findBasketUserByUsersIdAndIsTheBasketIsFalseAndIsRequestCreatedIsFalseAndIsIssuedIsTrue(Long basketUserId);


//    List<BasketUser> findBasketUserByUsersIdAndIsTheBasket(Long users_id);

//    BasketUser findBasketUserByUsersId(Long basketUserId);


}
