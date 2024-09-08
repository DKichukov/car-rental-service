package com.example.car_rental_service.controller;

import static com.example.car_rental_service.mapper.CarMapper.toEntity;
import static com.example.car_rental_service.utils.AuthUtil.createAuthenticationRequest;
import static com.example.car_rental_service.utils.AuthUtil.createUser;
import static com.example.car_rental_service.utils.AuthUtil.encodePassword;
import static com.example.car_rental_service.utils.AuthUtil.extractJwtToken;
import static com.example.car_rental_service.utils.CarUtils.createCarDto;
import static com.example.car_rental_service.utils.JsonUtil.convertToJson;
import static com.example.car_rental_service.utils.JsonUtil.parseResponseBody;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.car_rental_service.config.TestcontainersConfig;
import com.example.car_rental_service.dto.AuthenticationRequest;
import com.example.car_rental_service.dto.CarDto;
import com.example.car_rental_service.entity.User;
import com.example.car_rental_service.enums.UserRole;
import com.example.car_rental_service.repository.CarRepository;
import com.example.car_rental_service.repository.UserRepository;
import jakarta.inject.Inject;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;

@Testcontainers
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestcontainersConfig.class)
class CustomerControllerTest {
  private static final String CUSTOMER_NAME = "Customer";
  private static final String CUSTOMER_TEST_EMAIL = "customer@test.com";
  private static final String CUSTOMER_RAW_PASSWORD = "customer";

  private static final String AUTH_URL = "/api/auth";
  private static final String CUSTOMER_URL = "/api/customer";

  @Autowired
  MockMvc mockMvc;
  @Autowired
  UserRepository userRepository;
  @Inject
  CarRepository carRepository;
  @Autowired
  private PostgreSQLContainer<?> postgresContainer;


  @BeforeEach
  void setUp() {
    if (userRepository.count() > 0) {
      userRepository.deleteAll();
    }
    if (carRepository.count() > 0) {
      carRepository.deleteAll();
    }
  }

  @Test
  public void connectionEstablished() {
    assertTrue(postgresContainer.isCreated());
    assertTrue(postgresContainer.isRunning());
  }

  @Test
  void testGetAllCarsWithValidCustomerCredentials() throws Exception {
    createCustomerUser();

    CarDto carDto1 = createCarDto();
    CarDto carDto2 = createCarDto();
    carRepository.save(toEntity(carDto1));
    carRepository.save(toEntity(carDto2));

    String authRequestBody = createAuthRequest();

    MvcResult loginResult = mockMvc.perform(
        MockMvcRequestBuilders.post(AUTH_URL + "/login").contentType(MediaType.APPLICATION_JSON)
            .content(authRequestBody)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

    String jwtToken = extractJwtToken(loginResult);

    MvcResult carResult = mockMvc.perform(
            MockMvcRequestBuilders.get(CUSTOMER_URL + "/cars").contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + jwtToken))
        .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

    List<?> cars = parseResponseBody(carResult, List.class);

    assertEquals(2, cars.size());
  }

  private void createCustomerUser() {
    User customerUser =
        createUser(CUSTOMER_NAME, CUSTOMER_TEST_EMAIL, encodePassword(CUSTOMER_RAW_PASSWORD),
            UserRole.CUSTOMER);
    userRepository.save(customerUser);
  }

  private String createAuthRequest()
      throws JsonProcessingException, com.fasterxml.jackson.core.JsonProcessingException {
    AuthenticationRequest authenticationRequest =
        createAuthenticationRequest(CUSTOMER_TEST_EMAIL, CUSTOMER_RAW_PASSWORD);
    return convertToJson(authenticationRequest);
  }


}
