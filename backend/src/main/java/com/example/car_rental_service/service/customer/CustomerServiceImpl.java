package com.example.car_rental_service.service.customer;

import com.example.car_rental_service.dto.CarDto;
import com.example.car_rental_service.mapper.CarMapper;
import com.example.car_rental_service.repository.CarRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

  private final CarRepository carRepository;

  @Override
  public List<CarDto> getAllCars() {
    return carRepository.findAll().stream().map(CarMapper::toDto).toList();
  }
}
