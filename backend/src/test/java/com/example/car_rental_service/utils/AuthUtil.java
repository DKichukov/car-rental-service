package com.example.car_rental_service.utils;

import com.example.car_rental_service.dto.AuthenticationRequest;
import com.example.car_rental_service.entity.User;
import com.example.car_rental_service.enums.UserRole;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;

public class AuthUtil {

  private static final ObjectMapper objectMapper = new ObjectMapper();

  private AuthUtil() {
  }

  public static AuthenticationRequest createAuthenticationRequest(String email, String password) {
    AuthenticationRequest request = new AuthenticationRequest();
    request.setEmail(email);
    request.setPassword(password);
    return request;
  }

  public static String encodePassword(String password) {
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    return passwordEncoder.encode(password);
  }

  public static User createUser(String name, String mail, String password, UserRole role) {
    User user = new User();
    user.setName(name);
    user.setEmail(mail);
    user.setPassword(password);
    user.setUserRole(role);

    return user;
  }

  public static String extractJwtToken(MvcResult result)
      throws UnsupportedEncodingException, JsonProcessingException,
      com.fasterxml.jackson.core.JsonProcessingException {
    String responseBody = result.getResponse().getContentAsString();
    Map<String, String> responseMap =
        objectMapper.readValue(responseBody, new TypeReference<Map<String, String>>() {
        });
    return responseMap.get("jwt");
  }

}
