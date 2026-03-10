package com.hotel.auth_server.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.security.autoconfigure.SecurityProperties.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hotel.auth_server.dto.RequestCreateUser;
import com.hotel.auth_server.dto.ResponseDto;
import com.hotel.auth_server.dto.UserDTo;
import com.hotel.auth_server.entity.Role;
import com.hotel.auth_server.entity.UserEntiy;
import com.hotel.auth_server.enums.RoleName;
import com.hotel.auth_server.repository.RoleRepository;
import com.hotel.auth_server.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserEntityServiceImpl implements UserDetailsService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public ResponseDto createUser(RequestCreateUser userData) {

        userRepository.findByUsername(userData.username()).ifPresent(user -> {
            throw new RuntimeException("Username already exists");
        });

        UserEntiy newUser = UserEntiy.builder()
                .username(userData.username())
                .password(passwordEncoder.encode(userData.password()))
                .email(userData.email())
                .build();
        Set<Role> roles = new HashSet<>();
        userData.roles().forEach(r -> {
            Role role = roleRepository.findByRole(RoleName.valueOf(r))
            .orElseThrow(()-> new RuntimeException("Role not found: " + r));
            roles.add(role);
        });     
        newUser.setRole(roles);
        userRepository.save(newUser);
        return new ResponseDto("User created successfully", true);
    }

    // Get user by username
    public UserDTo getUserByUsername(String username) {
        UserEntiy user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return new UserDTo(user.getId(), user.getUsername(), user.getEmail());
    }


}
