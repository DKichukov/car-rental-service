package com.example.car_rental_service.dto;

import com.example.car_rental_service.enums.UserRole;
import lombok.Data;

@Data
public class AuthenticationResponse {
    private String jwt;
    private UserRole userRole;
    private Integer userId;

}
