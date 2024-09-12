package com.example.car_rental_service.service.auth;

import static com.example.car_rental_service.mapper.UserMapper.toUser;

import com.example.car_rental_service.dto.SignupRequest;
import com.example.car_rental_service.dto.UserDto;
import com.example.car_rental_service.entity.User;
import com.example.car_rental_service.enums.UserRole;
import com.example.car_rental_service.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final UserRepository userRepository;

  //TODO: for development purpose only, remove afte
  @PostConstruct
  public void createAdminAccount() {
    User adminAccount = userRepository.findByUserRole(UserRole.ADMIN);
    if (adminAccount == null) {
      User newAdminAccount = new User();
      newAdminAccount.setName("Admin");
      newAdminAccount.setEmail("admin@gmail.com");
      newAdminAccount.setPassword(new BCryptPasswordEncoder().encode("admin"));
      newAdminAccount.setUserRole(UserRole.ADMIN);
      userRepository.save(newAdminAccount);
    }
  }

  @Override
  public UserDto createCustomer(SignupRequest signupRequest) {

    if (signupRequest.email() == null) {
      return null;
    }
    User user = toUser(signupRequest, UserRole.CUSTOMER);
    user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.password()));
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
