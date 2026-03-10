package com.hotel.auth_server.dto;

import java.util.List;

public record RequestCreateUser(
    String username,
    String email,   
    String password,
    List<String> roles
) {

}
