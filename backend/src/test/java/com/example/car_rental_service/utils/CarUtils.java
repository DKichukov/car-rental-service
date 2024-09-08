package com.example.car_rental_service.utils;

import com.example.car_rental_service.dto.CarDto;
import java.util.Date;

public class CarUtils {

  private CarUtils() {

  }

  public static CarDto createCarDto() {
    CarDto carDto = new CarDto();
    carDto.setBrand("bwm");
    carDto.setColor("white");
    carDto.setName("name");
    carDto.setTransmission("transmission");
    carDto.setType("type");
    carDto.setDescription("Test description");
    carDto.setPrice(100L);
    carDto.setYear(new Date(2024, 9, 1));
    carDto.setImage(null);

    return carDto;
  }
}
