package com.hotel.auth_server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import com.hotel.auth_server.repository.RoleRepository;

@SpringBootApplication
@EnableDiscoveryClient
public class AuthServerApplication /*implements  CommandLineRunner */{

	@Autowired
	RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(AuthServerApplication.class, args);
	}

	// @Override
	// public void run(String... args) throws Exception {
	// 	Role adminRole = Role.builder().role(RoleName.ROLE_ADMIN).build();
	// 	Role userRole = Role.builder().role(RoleName.ROLE_USER).build();
	// 	roleRepository.save(adminRole);
	// 	roleRepository.save(userRole);
	// }
}
