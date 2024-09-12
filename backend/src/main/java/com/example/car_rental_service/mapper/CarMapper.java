package com.example.car_rental_service.mapper;

import com.example.car_rental_service.dto.CarDto;
import com.example.car_rental_service.entity.Car;
import java.io.IOException;

public final class CarMapper {

  private CarMapper() {
  }

  public static Car toEntity(CarDto carDto) throws IOException {
    Car car = new Car();
    car.setId(carDto.getId());
    car.setBrand(carDto.getBrand());
    car.setColor(carDto.getColor());
    car.setName(carDto.getName());
    car.setTransmission(carDto.getTransmission());
    car.setType(carDto.getType());
    car.setDescription(carDto.getDescription());
    car.setPrice(carDto.getPrice());
    car.setYear(carDto.getYear());
    car.setImage(carDto.getImage() != null ? carDto.getImage().getBytes() : null);

    return car;
  }

  public static CarDto toDto(Car car) {
    CarDto carDto = new CarDto();
    carDto.setId(car.getId());
    carDto.setBrand(car.getBrand());
    carDto.setColor(car.getColor());
    carDto.setName(car.getName());
    carDto.setTransmission(car.getTransmission());
    carDto.setType(car.getType());
    carDto.setDescription(car.getDescription());
    carDto.setPrice(car.getPrice());
    carDto.setYear(car.getYear());
    carDto.setReturnedImage(car.getImage() != null ? car.getImage() : null);

    return carDto;
  }

}
