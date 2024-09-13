package com.example.car_rental_service.service.admin;

import static com.example.car_rental_service.mapper.CarMapper.toEntity;

import com.example.car_rental_service.dto.BookACarDto;
import com.example.car_rental_service.dto.CarDto;
import com.example.car_rental_service.entity.BookACar;
import com.example.car_rental_service.entity.Car;
import com.example.car_rental_service.enums.BookCarStatus;
import com.example.car_rental_service.mapper.BookACarMapper;
import com.example.car_rental_service.mapper.CarMapper;
import com.example.car_rental_service.repository.BookACarRepository;
import com.example.car_rental_service.repository.CarRepository;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AdminServiceImpl implements AdminService {

  private final CarRepository carRepository;
  private final BookACarRepository bookACarRepository;

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

  @Override
  public List<CarDto> getAllCars() {
    return carRepository.findAll().stream().map(CarMapper::toDto).toList();
  }

  @Override
  public void deleteCar(Integer id) {
    Optional<Car> foundCar = carRepository.findById(id);
    foundCar.ifPresent(carRepository::delete);
  }

  @Override
  public CarDto getCarById(Integer id) {
    Optional<Car> foundCar = carRepository.findById(id);
    return foundCar.map(CarMapper::toDto).orElse(null);
  }

  @Override
  public boolean updateCar(Integer carId, CarDto carDto) throws IOException {
    Optional<Car> foundCar = carRepository.findById(carId);
    if (foundCar.isPresent()) {
      Car existingCar = foundCar.get();
      if (carDto.getImage() != null) {
        existingCar.setImage(carDto.getImage().getBytes());
      }

      existingCar.setPrice(carDto.getPrice());
      existingCar.setYear(carDto.getYear());
      existingCar.setType(carDto.getType());
      existingCar.setDescription(carDto.getDescription());
      existingCar.setTransmission(carDto.getTransmission());
      existingCar.setColor(carDto.getColor());
      existingCar.setName(carDto.getName());
      existingCar.setBrand(carDto.getBrand());

      carRepository.save(existingCar);
      return true;
    } else {
      return false;
    }
  }

  @Override
  public List<BookACarDto> getBookings() {
    return bookACarRepository.findAll().stream().map(BookACarMapper::toDto).toList();
  }

  @Override
  public boolean changeBookingStatus(Integer bookingId, String status) {
    Optional<BookACar> optionalBookACar = bookACarRepository.findById(bookingId);

    if (optionalBookACar.isPresent()) {
      BookACar bookACar = optionalBookACar.get();

      if ("Approve".equalsIgnoreCase(status)) {
        bookACar.setBookCarStatus(BookCarStatus.APPROVED);
      } else if ("Reject".equalsIgnoreCase(status)) {
        bookACar.setBookCarStatus(BookCarStatus.REJECTED);
      }
      bookACarRepository.save(bookACar);
      return true;
    }
    return false;
  }
}
