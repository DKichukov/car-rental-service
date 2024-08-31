package com.example.car_rental_service.controller;

import com.example.car_rental_service.dto.SignupRequest;
import com.example.car_rental_service.dto.UserDto;
import com.example.car_rental_service.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> signupCustomer(@RequestBody SignupRequest signupRequest) {
        if (authService.hasCustomerWithEmail(signupRequest.email())) {
            return new ResponseEntity<>("Customer already exists!", HttpStatus.NOT_ACCEPTABLE );
        }
        UserDto createCustomerDto = authService.createCustomer(signupRequest);
        if (createCustomerDto == null) {
            return new ResponseEntity<>("Customer not created!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(createCustomerDto, HttpStatus.CREATED);
    }

}
