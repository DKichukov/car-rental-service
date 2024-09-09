package com.example.car_rental_service.controller;

import com.example.car_rental_service.dto.BookACarDto;
import com.example.car_rental_service.dto.CarDto;
import com.example.car_rental_service.service.customer.CustomerService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

  @PostMapping("/car/book")
  public ResponseEntity<Void> bookACar(@RequestBody BookACarDto bookACarDto) {
    boolean success = customerService.bookACar(bookACarDto);
    if (success) {
      return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
  }

  @GetMapping("/car/{carId}")
  public ResponseEntity<CarDto> getCarById(@PathVariable Integer carId) {
    CarDto carDto = customerService.getCarById(carId);
    if (carDto == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(carDto);
  }
}
