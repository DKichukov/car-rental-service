package com.example.car_rental_service.controller;

import com.example.car_rental_service.dto.CarDto;
import com.example.car_rental_service.service.admin.AdminService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

  private final AdminService adminService;

  @PostMapping("/car")
  public ResponseEntity<?> postCar(@ModelAttribute CarDto carDto) throws IOException {
    boolean success = adminService.postCar(carDto);
    if (success) {
      return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
  }

  @GetMapping("/cars")
  public ResponseEntity<?> getAllCars() {
    return ResponseEntity.ok(adminService.getAllCars());
  }

  @DeleteMapping("/car/{id}")
  public ResponseEntity<Void> deleteCar(@PathVariable Integer id) {
    adminService.deleteCar(id);
    return ResponseEntity.ok(null);
  }

  @GetMapping("/car/{id}")
  public ResponseEntity<CarDto> getCarById(@PathVariable Integer id) {
    CarDto carDto = adminService.getCarById(id);
    return ResponseEntity.ok(carDto);
  }

  @PutMapping("/car/{carId}")
  public ResponseEntity<Void> updateCar(@PathVariable Integer carId, @ModelAttribute CarDto carDto)
      throws IOException {
    try {
      boolean success = adminService.updateCar(carId, carDto);
      if (success) {
        return ResponseEntity.status(HttpStatus.OK).build();
      }
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }
}
