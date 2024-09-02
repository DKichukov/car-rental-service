package com.example.car_rental_service.controller;

import com.example.car_rental_service.dto.AuthenticationRequest;
import com.example.car_rental_service.dto.AuthenticationResponse;
import com.example.car_rental_service.dto.SignupRequest;
import com.example.car_rental_service.dto.UserDto;
import com.example.car_rental_service.entity.User;
import com.example.car_rental_service.repository.UserRepository;
import com.example.car_rental_service.service.auth.AuthService;
import com.example.car_rental_service.service.jwt.UserService;
import com.example.car_rental_service.config.JwtTokenService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtTokenService jwtTokenService;

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

    @PostMapping("/login")
    public AuthenticationResponse createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws BadCredentialsException,
        DisabledException, UsernameNotFoundException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getEmail(),
                authenticationRequest.getPassword()
            ));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect username or password.");
        } catch (DisabledException e) {
            throw new DisabledException("User is disabled.");
        } catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException("User not found.");
        }

        final UserDetails userDetails = userService.userDetailsService().loadUserByUsername(authenticationRequest.getEmail());
        Optional<User> optionalUser = userRepository.findFirstByEmail(userDetails.getUsername());

        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("User not found.");
        }

        final String jwt = jwtTokenService.generateToken(userDetails);

        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setJwt(jwt);
        authenticationResponse.setUserId(optionalUser.get().getId());
        authenticationResponse.setUserRole(optionalUser.get().getUserRole());

        return authenticationResponse;
    }
}
