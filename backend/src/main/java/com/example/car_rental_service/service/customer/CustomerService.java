package com.example.car_rental_service.service.customer;

import com.example.car_rental_service.dto.CarDto;
import java.util.List;

public interface CustomerService {

  List<CarDto> getAllCars();
}
