package com.app.persistence.entity.repository;

import com.app.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository <UserEntity, Long> {
  // query methods  de jpa
    Optional<UserEntity>findUserEntityByUsername (String username);

    // o tambien
//    @Query ("SELECT  u from UserEntity  u WHERE u.ussername =  ? " )
//    Optional<UserEntity>findUser();
}
