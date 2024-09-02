package com.example.car_rental_service.service.auth;

import static com.example.car_rental_service.mapper.UserMapper.toCustomer;
import static com.example.car_rental_service.mapper.UserMapper.toCustomerDto;

import com.example.car_rental_service.dto.SignupRequest;
import com.example.car_rental_service.dto.UserDto;
import com.example.car_rental_service.entity.User;
import com.example.car_rental_service.enums.UserRole;
import com.example.car_rental_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
        User user = new User();
        user.setEmail(signupRequest.email());
        user.setName(signupRequest.name());
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.password()));
        user.setUserRole(UserRole.CUSTOMER);
        userRepository.save(user);

        UserDto userDto = new UserDto();
        userDto.setId(user.getId());

        return userDto;
    }

    @Override
    public boolean hasCustomerWithEmail(String email) {
        return userRepository.findFirstByEmail(email).isPresent();
    }
}
