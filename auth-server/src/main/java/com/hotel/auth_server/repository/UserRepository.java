package com.hotel.auth_server.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hotel.auth_server.entity.UserEntiy;

@Repository
public interface UserRepository extends JpaRepository<UserEntiy, Long> {
    Optional<UserEntiy> findByUsername(String username);

}
