package com.ptho1504.microservices.user_service.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ptho1504.microservices.user_service.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
