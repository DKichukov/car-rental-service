package com.example.car_rental_service.controller;

import com.example.car_rental_service.dto.CarDto;
import com.example.car_rental_service.service.admin.AdminService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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
}
