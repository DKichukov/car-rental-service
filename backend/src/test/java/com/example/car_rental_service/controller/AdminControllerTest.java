package com.example.car_rental_service.controller;

import static com.example.car_rental_service.mapper.CarMapper.toEntity;
import static com.example.car_rental_service.utils.AuthUtil.createAuthenticationRequest;
import static com.example.car_rental_service.utils.AuthUtil.createUser;
import static com.example.car_rental_service.utils.AuthUtil.encodePassword;
import static com.example.car_rental_service.utils.AuthUtil.extractJwtToken;
import static com.example.car_rental_service.utils.JsonUtil.convertToJson;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.car_rental_service.config.TestcontainersConfig;
import com.example.car_rental_service.dto.AuthenticationRequest;
import com.example.car_rental_service.dto.CarDto;
import com.example.car_rental_service.entity.User;
import com.example.car_rental_service.enums.UserRole;
import com.example.car_rental_service.repository.CarRepository;
import com.example.car_rental_service.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import java.util.Arrays;
import java.util.Date;
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

@Testcontainers
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestcontainersConfig.class)
class AdminControllerTest {

  private static final String ADMIN_NAME = "Admin";
  private static final String ADMIN_TEST_EMAIL = "admin@test.com";
  private static final String ADMIN_RAW_PASSWORD = "admin";

  private static final String AUTH_URL = "/api/auth";
  private static final String ADMIN_URL = "/api/admin";

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
  void testCarPostRequestWithValidAdminCredentials() throws Exception {
    User adminUser = createUser(ADMIN_NAME, ADMIN_TEST_EMAIL, encodePassword(ADMIN_RAW_PASSWORD),
        UserRole.ADMIN);
    userRepository.save(adminUser);
    CarDto carDto = createCarDto();

    AuthenticationRequest authenticationRequest =
        createAuthenticationRequest(ADMIN_TEST_EMAIL, ADMIN_RAW_PASSWORD);
    String authRequestBody = convertToJson(authenticationRequest);

    MvcResult loginResult = mockMvc.perform(
        MockMvcRequestBuilders.post(AUTH_URL + "/login").contentType(MediaType.APPLICATION_JSON)
            .content(authRequestBody)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

    String jwtToken = extractJwtToken(loginResult);
    String carRequestBody = convertToJson(carDto);

    MvcResult carResult = mockMvc.perform(
            MockMvcRequestBuilders.post(ADMIN_URL + "/car").contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + jwtToken).content(carRequestBody))
        .andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();

    assertTrue(carRepository.count() > 0);

  }

  @Test
  void testGetAllCarsWithValidAdminCredentials() throws Exception {
    User adminUser = createUser(ADMIN_NAME, ADMIN_TEST_EMAIL, encodePassword(ADMIN_RAW_PASSWORD),
        UserRole.ADMIN);
    userRepository.save(adminUser);

    CarDto carDto1 = createCarDto();
    CarDto carDto2 = createCarDto();
    carRepository.save(toEntity(carDto1));
    carRepository.save(toEntity(carDto2));

    AuthenticationRequest authenticationRequest =
        createAuthenticationRequest(ADMIN_TEST_EMAIL, ADMIN_RAW_PASSWORD);
    String authRequestBody = convertToJson(authenticationRequest);

    MvcResult loginResult = mockMvc.perform(
        MockMvcRequestBuilders.post(AUTH_URL + "/login").contentType(MediaType.APPLICATION_JSON)
            .content(authRequestBody)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

    String jwtToken = extractJwtToken(loginResult);

    MvcResult carResult = mockMvc.perform(
            MockMvcRequestBuilders.get(ADMIN_URL + "/cars").contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + jwtToken))
        .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

    String responseContent = carResult.getResponse().getContentAsString();
    List<CarDto> cars =
        Arrays.asList(new ObjectMapper().readValue(responseContent, CarDto[].class));

    assertEquals(2, cars.size());
  }

  private CarDto createCarDto() {
    CarDto carDto = new CarDto();
    carDto.setBrand("bwm");
    carDto.setColor("white");
    carDto.setName("name");
    carDto.setTransmission("transmission");
    carDto.setType("type");
    carDto.setDescription("Test description");
    carDto.setPrice(100L);
    carDto.setYear(new Date(2024, 9, 1));
    carDto.setImage(null);

    return carDto;
  }
}
