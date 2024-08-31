package com.example.car_rental_service.dto;

import com.example.car_rental_service.enums.UserRole;
import lombok.Data;

@Data
public class UserDto {

    private Integer id;
    private String name;
    private String email;
    private UserRole userRole;

}
