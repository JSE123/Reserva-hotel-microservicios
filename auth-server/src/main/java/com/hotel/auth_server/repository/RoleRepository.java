package com.hotel.auth_server.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotel.auth_server.entity.Role;
import com.hotel.auth_server.enums.RoleName;

public interface RoleRepository extends JpaRepository<Role, Long> {
        Optional<Role> findByRole(RoleName roleName);


}
