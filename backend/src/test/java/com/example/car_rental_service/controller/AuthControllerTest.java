package com.example.car_rental_service.controller;

import static com.example.car_rental_service.enums.UserRole.CUSTOMER;
import static com.example.car_rental_service.mapper.UserMapper.toUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.car_rental_service.dto.AuthenticationRequest;
import com.example.car_rental_service.dto.AuthenticationResponse;
import com.example.car_rental_service.dto.SignupRequest;
import com.example.car_rental_service.dto.UserDto;
import com.example.car_rental_service.entity.User;
import com.example.car_rental_service.enums.UserRole;
import com.example.car_rental_service.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AuthControllerTest {

    private static final String AUTH_URL = "/api/auth";
    private static final String TEST_EMAIL = "test@example.com";
    private static final String TEST_NAME = "John Doe";
    private static final String TEST_PASSWORD = "123456789";

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:14.13-alpine3.20");

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        if(userRepository.count() > 0) {
            userRepository.deleteAll();
        }
    }

    private static @NotNull SignupRequest createSignupRequest(String mail, String name, String password) {
        return new SignupRequest(mail, name, password);
    }

    @Test
    public void connectionEstablished() {
        assertTrue(postgres.isCreated());
        assertTrue(postgres.isRunning());
    }

    @Test
    void testCreateCustomerReturnsCreatedStatus() throws Exception {
        SignupRequest signupRequest = createSignupRequest(TEST_EMAIL, TEST_NAME, TEST_PASSWORD);
        String requestBody = convertToJson(signupRequest);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(AUTH_URL + "/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andReturn();

        UserDto response = parseResponseBody(result, UserDto.class);

        assertNotNull(result.getResponse().getContentAsString());
    }

    @Test
    void testCreateCustomerCustomerAlreadyExistsReturnsNotAcceptable() throws Exception {
        SignupRequest existingUser = createSignupRequest(TEST_EMAIL, TEST_NAME, TEST_PASSWORD);
        userRepository.save(toUser(existingUser, CUSTOMER));

        String requestBody = convertToJson(existingUser);

        mockMvc.perform(MockMvcRequestBuilders.post(AUTH_URL + "/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
            .andExpect(MockMvcResultMatchers.status().isNotAcceptable())
            .andExpect(MockMvcResultMatchers.content().string("Customer already exists!"));
    }

    @Test
    void testCreateCustomerCreationFailsReturnsBadRequest() throws Exception {
        SignupRequest signupRequest = new SignupRequest(null, null, null);
        String requestBody = convertToJson(signupRequest);

        mockMvc.perform(MockMvcRequestBuilders.post(AUTH_URL + "/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void testSuccessfulCustomerLoginReturnsUserDetails() throws Exception {
        User user = userRepository.save(createUser(TEST_NAME, TEST_EMAIL, encodePassword(TEST_PASSWORD), CUSTOMER));
        AuthenticationRequest authenticationRequest = createAuthenticationRequest(TEST_EMAIL, TEST_PASSWORD);

        String requestBody = convertToJson(authenticationRequest);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(AUTH_URL + "/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn();

        AuthenticationResponse response = parseResponseBody(result, AuthenticationResponse.class);

        assertNotNull(result.getResponse().getContentAsString());
        assertNotNull(response.getJwt());
        assertEquals(user.getId(), response.getUserId());
        assertEquals(CUSTOMER, response.getUserRole());
    }
    @Test
    void testFailedCustomerLoginReturnsUnauthorized() throws Exception {
      String wrongPassword = "wrong_password";
        User user = userRepository.save(createUser(TEST_NAME, TEST_EMAIL, encodePassword(TEST_PASSWORD), CUSTOMER));
        AuthenticationRequest authenticationRequest = createAuthenticationRequest(TEST_EMAIL, wrongPassword);

        String requestBody = convertToJson(authenticationRequest);

        mockMvc.perform(MockMvcRequestBuilders.post(AUTH_URL + "/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
            .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    private String convertToJson(Object object) throws Exception {
        return objectMapper.writeValueAsString(object);
    }

    private <T> T parseResponseBody(MvcResult result, Class<T> responseType) throws Exception {
        return objectMapper.readValue(result.getResponse().getContentAsString(), responseType);
    }

    private String encodePassword(String password) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    private AuthenticationRequest createAuthenticationRequest(String email, String password) {
        AuthenticationRequest request = new AuthenticationRequest();
        request.setEmail(email);
        request.setPassword(password);
        return request;
    }

    private User createUser(String name, String mail, String password, UserRole role) {
        User user = new User();
        user.setName(name);
        user.setEmail(mail);
        user.setPassword(password);
        user.setUserRole(role);

        return user;
    }

}