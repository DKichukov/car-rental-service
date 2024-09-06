package com.example.car_rental_service;

import com.example.car_rental_service.config.TestcontainersConfig;
import org.springframework.boot.SpringApplication;

public class TestCarRentalServiceApplication {

  public static void main(String[] args) {
    SpringApplication.from(CarRentalServiceApplication::main).with(TestcontainersConfig.class)
        .run(args);
  }

}
