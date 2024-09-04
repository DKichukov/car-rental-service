package com.example.car_rental_service.service.admin;

import static com.example.car_rental_service.mapper.CarMapper.toEntity;

import com.example.car_rental_service.dto.CarDto;
import com.example.car_rental_service.entity.Car;
import com.example.car_rental_service.repository.CarRepository;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AdminServiceImpl implements AdminService {

  private final CarRepository carRepository;

  @Override
  public boolean postCar(CarDto carDto) {
    try {
      Car car = toEntity(carDto);
      carRepository.save(car);
      return true;
    } catch (IOException e) {
      return false;
    }
  }
}
