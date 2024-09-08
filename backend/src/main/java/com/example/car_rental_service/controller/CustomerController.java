package com.example.car_rental_service.controller;

import com.example.car_rental_service.dto.CarDto;
import com.example.car_rental_service.service.customer.CustomerService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customer")
public class CustomerController {

  private final CustomerService customerService;

  @GetMapping("/cars")
  public ResponseEntity<List<CarDto>> getAllCars() {
    List<CarDto> carDtoList = customerService.getAllCars();
    return ResponseEntity.ok(carDtoList);
  }
}
