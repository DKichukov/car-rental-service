package com.example.car_rental_service.utils;

import com.example.car_rental_service.dto.BookACarDto;
import com.example.car_rental_service.dto.CarDto;
import com.example.car_rental_service.entity.BookACar;
import com.example.car_rental_service.entity.Car;
import com.example.car_rental_service.entity.User;
import com.example.car_rental_service.enums.BookCarStatus;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TestDataGenerator {

  private TestDataGenerator() {

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

  public static BookACarDto createDummyBookACarDto(Integer userId, Integer carId) {
    BookACarDto bookACarDto = new BookACarDto();
    bookACarDto.setId(1);
    bookACarDto.setFromDate(new Date());
    bookACarDto.setToDate(new Date(System.currentTimeMillis() + 86400000));
    bookACarDto.setDays(1L);
    bookACarDto.setPrice(100L);
    bookACarDto.setBookCarStatus(BookCarStatus.PENDING);
    bookACarDto.setUserId(userId);
    bookACarDto.setCarId(carId);

    return bookACarDto;
  }

  public static BookACarDto createBookACarDto(Integer userId, Integer carId) {
    BookACarDto bookACarDto = new BookACarDto();
    bookACarDto.setUserId(userId);
    bookACarDto.setCarId(carId);
    bookACarDto.setBookCarStatus(BookCarStatus.PENDING);
    bookACarDto.setFromDate(new Date());
    bookACarDto.setToDate(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1)));
    return bookACarDto;
  }

  public static BookACar createBookACarEntity(BookACarDto bookACarDto, User customerUser,
      Car savedCar) {
    BookACar saveBooking = new BookACar();
    saveBooking.setToDate(bookACarDto.getToDate());
    saveBooking.setFromDate(bookACarDto.getFromDate());
    saveBooking.setBookCarStatus(bookACarDto.getBookCarStatus());
    saveBooking.setUser(customerUser);
    saveBooking.setCar(savedCar);
    return saveBooking;
  }
}
