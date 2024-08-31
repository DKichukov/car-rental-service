package com.example.car_rental_service.mapper;

import com.example.car_rental_service.dto.SignupRequest;
import com.example.car_rental_service.entity.User;
import com.example.car_rental_service.enums.UserRole;
import com.example.car_rental_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;

public class UserMapper {

    public static User toCustomer(SignupRequest signupRequest) {
        User user = new User();
        user.setEmail(signupRequest.email());
        user.setName(signupRequest.name());
        user.setPassword(signupRequest.password());
        user.setUserRole(UserRole.CUSTOMER);

        return user;
    }

}
