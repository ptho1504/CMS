package com.ptho1504.microservices.user_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ptho1504.microservices.user_service.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findOneByEmail(String email);

    Optional<User> findByEmail(String email);
}
