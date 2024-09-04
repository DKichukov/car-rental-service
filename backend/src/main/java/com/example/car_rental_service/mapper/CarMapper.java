package com.example.car_rental_service.mapper;

import com.example.car_rental_service.dto.CarDto;
import com.example.car_rental_service.entity.Car;
import java.io.IOException;

public class CarMapper {

  public static Car toEntity(CarDto carDto) throws IOException {
    Car car = new Car();
    car.setId(carDto.getId());
    car.setBrand(car.getBrand());
    car.setColor(carDto.getColor());
    car.setName(carDto.getName());
    car.setType(carDto.getType());
    car.setDescription(carDto.getDescription());
    car.setPrice(carDto.getPrice());
    car.setYear(carDto.getYear());
    car.setImage(carDto.getImage().getBytes());

    return car;
  }

}
