package com.example.car_rental_service.service.admin;

import com.example.car_rental_service.dto.CarDto;
import java.io.IOException;

public interface AdminService {

  boolean postCar(CarDto carDto) throws IOException;
}
