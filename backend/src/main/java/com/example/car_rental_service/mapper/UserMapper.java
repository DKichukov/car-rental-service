package com.example.car_rental_service.mapper;

import com.example.car_rental_service.dto.SignupRequest;
import com.example.car_rental_service.dto.UserDto;
import com.example.car_rental_service.entity.User;
import com.example.car_rental_service.enums.UserRole;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserMapper {

    public static User toCustomer(SignupRequest signupRequest) {
        User user = new User();
        user.setEmail(signupRequest.email());
        user.setName(signupRequest.name());
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.password()));
        user.setUserRole(UserRole.CUSTOMER);

        return user;
    }

    public static UserDto toCustomerDto(User user) {
       UserDto userDto = new UserDto();
       userDto.setId(user.getId());
       userDto.setEmail(user.getEmail());
       userDto.setName(user.getName());
       userDto.setUserRole(user.getUserRole());

       return userDto;
    }
}
