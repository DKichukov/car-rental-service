package com.example.car_rental_service.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;

@TestConfiguration()
public class TestcontainersConfig {

  @Bean
  @ServiceConnection
  public PostgreSQLContainer<?> postgresContainer() {
    PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:14.13-alpine3.20");
    postgres.start();
    return postgres;
  }

}
