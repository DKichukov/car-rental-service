package com.example.car_rental_service.service.admin;

import com.example.car_rental_service.dto.BookACarDto;
import com.example.car_rental_service.dto.CarDto;
import com.example.car_rental_service.dto.CarDtoList;
import com.example.car_rental_service.dto.SearchCarDto;
import java.io.IOException;
import java.util.List;

public interface AdminService {

  boolean postCar(CarDto carDto) throws IOException;

  List<CarDto> getAllCars();

  void deleteCar(Integer id);

  CarDto getCarById(Integer id);

  boolean updateCar(Integer carId, CarDto carDto) throws IOException;

  List<BookACarDto> getBookings();

  boolean changeBookingStatus(Integer bookingId, String status);

  CarDtoList searchCar(SearchCarDto searchCarDto);
}
