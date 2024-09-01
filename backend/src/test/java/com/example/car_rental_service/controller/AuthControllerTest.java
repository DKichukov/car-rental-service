package com.example.car_rental_service.controller;

import static com.example.car_rental_service.mapper.UserMapper.toCustomer;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.car_rental_service.dto.SignupRequest;
import com.example.car_rental_service.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
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

        SignupRequest response = parseResponseBody(result, SignupRequest.class);

        assertNotNull(result.getResponse().getContentAsString());
        assertEquals(signupRequest.name(), response.name());
        assertEquals(signupRequest.email(), response.email());
    }

    @Test
    void testCreateCustomer_CustomerAlreadyExists_ReturnsNotAcceptable() throws Exception {
        SignupRequest existingUser = createSignupRequest(TEST_EMAIL, TEST_NAME, TEST_PASSWORD);
        userRepository.save(toCustomer(existingUser));

        String requestBody = convertToJson(existingUser);

        mockMvc.perform(MockMvcRequestBuilders.post(AUTH_URL + "/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
            .andExpect(MockMvcResultMatchers.status().isNotAcceptable())
            .andExpect(MockMvcResultMatchers.content().string("Customer already exists!"));
    }

    @Test
    void testCreateCustomer_CreationFails_ReturnsBadRequest() throws Exception {
        SignupRequest signupRequest = new SignupRequest(null, null, null);
        String requestBody = convertToJson(signupRequest);

        mockMvc.perform(MockMvcRequestBuilders.post(AUTH_URL + "/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    private String convertToJson(Object object) throws Exception {
        return objectMapper.writeValueAsString(object);
    }

    private <T> T parseResponseBody(MvcResult result, Class<T> responseType) throws Exception {
        return objectMapper.readValue(result.getResponse().getContentAsString(), responseType);
    }
}