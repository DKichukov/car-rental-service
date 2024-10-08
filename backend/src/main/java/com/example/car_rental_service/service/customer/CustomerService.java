package com.example.car_rental_service.service.customer;

import com.example.car_rental_service.dto.BookACarDto;
import com.example.car_rental_service.dto.CarDto;
import com.example.car_rental_service.dto.CarDtoList;
import com.example.car_rental_service.dto.SearchCarDto;
import java.util.List;

public interface CustomerService {

  List<CarDto> getAllCars();

  boolean bookACar(BookACarDto bookACarDto);

  CarDto getCarById(Integer id);

  List<BookACarDto> getBookingsByUserId(Integer userId);

  CarDtoList searchCar(SearchCarDto searchCarDto);
}
