package com.example.car_rental_service.service.auth;

import static com.example.car_rental_service.mapper.UserMapper.toCustomer;
import static com.example.car_rental_service.mapper.UserMapper.toCustomerDto;

import com.example.car_rental_service.dto.SignupRequest;
import com.example.car_rental_service.dto.UserDto;
import com.example.car_rental_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    @Override
    public UserDto createCustomer(SignupRequest signupRequest) {

        if(signupRequest.email()== null) {
            return null;
        }
        return toCustomerDto(userRepository.save(toCustomer(signupRequest)));
    }

    @Override
    public boolean hasCustomerWithEmail(String email) {
        return userRepository.findFirstByEmail(email).isPresent();
    }
}
