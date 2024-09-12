package com.example.car_rental_service.mapper;

import com.example.car_rental_service.dto.SignupRequest;
import com.example.car_rental_service.entity.User;
import com.example.car_rental_service.enums.UserRole;

public final class UserMapper {

  private UserMapper() {
  }

  public static User toUser(SignupRequest signupRequest, UserRole userRole) {
    User user = new User();
    user.setEmail(signupRequest.email());
    user.setName(signupRequest.name());
    user.setPassword(signupRequest.password());
    user.setUserRole(userRole);

    return user;
  }
}
