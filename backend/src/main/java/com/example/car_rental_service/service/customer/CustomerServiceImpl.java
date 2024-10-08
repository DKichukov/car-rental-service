package com.example.car_rental_service.service.customer;

import com.example.car_rental_service.dto.BookACarDto;
import com.example.car_rental_service.dto.CarDto;
import com.example.car_rental_service.dto.CarDtoList;
import com.example.car_rental_service.dto.SearchCarDto;
import com.example.car_rental_service.entity.BookACar;
import com.example.car_rental_service.entity.Car;
import com.example.car_rental_service.entity.User;
import com.example.car_rental_service.enums.BookCarStatus;
import com.example.car_rental_service.mapper.BookACarMapper;
import com.example.car_rental_service.mapper.CarMapper;
import com.example.car_rental_service.repository.BookACarRepository;
import com.example.car_rental_service.repository.CarRepository;
import com.example.car_rental_service.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

  private final CarRepository carRepository;
  private final UserRepository userRepository;
  private final BookACarRepository bookACarRepository;

  @Override
  public List<CarDto> getAllCars() {
    return carRepository.findAll().stream().map(CarMapper::toDto).toList();
  }

  @Override
  public boolean bookACar(BookACarDto bookACarDto) {
    Optional<Car> optionalCar = carRepository.findById(bookACarDto.getCarId());
    Optional<User> optionalUser = userRepository.findById(bookACarDto.getUserId());

    if (optionalCar.isPresent() && optionalUser.isPresent()) {
      BookACar bookACar = new BookACar();
      Car existingCar = optionalCar.get();
      bookACar.setUser(optionalUser.get());
      bookACar.setFromDate(bookACarDto.getFromDate());
      bookACar.setToDate(bookACarDto.getToDate());
      bookACar.setCar(existingCar);
      bookACar.setBookCarStatus(BookCarStatus.PENDING);
      long diffInMilliSeconds =
          bookACarDto.getToDate().getTime() - bookACarDto.getFromDate().getTime();
      long days = TimeUnit.MILLISECONDS.toDays(diffInMilliSeconds);
      bookACar.setDays(days);
      bookACar.setPrice(existingCar.getPrice() * days);
      bookACarRepository.save(bookACar);
      return true;
    }
    return false;
  }

  @Override
  public CarDto getCarById(Integer id) {
    Optional<Car> optionalCar = carRepository.findById(id);
    return optionalCar.map(CarMapper::toDto).orElse(null);
  }

  @Override
  public List<BookACarDto> getBookingsByUserId(Integer userId) {
    return bookACarRepository.findAllByUserId(userId).stream().map(BookACarMapper::toDto).toList();
  }

  @Override
  public CarDtoList searchCar(SearchCarDto searchCarDto) {
    Car car = new Car();
    car.setBrand(searchCarDto.getBrand());
    car.setType(searchCarDto.getType());
    car.setTransmission(searchCarDto.getTransmission());
    car.setColor(searchCarDto.getColor());
    ExampleMatcher matcher =
        ExampleMatcher.matching().withMatcher("brand", match -> match.ignoreCase().startsWith())
            .withMatcher("type", match -> match.ignoreCase().startsWith())
            .withMatcher("transmission", match -> match.ignoreCase().startsWith())
            .withMatcher("color", match -> match.ignoreCase().startsWith());
    Example<Car> carExample = Example.of(car, matcher);
    List<Car> cars = carRepository.findAll(carExample);
    CarDtoList carDtoList = new CarDtoList();
    carDtoList.setCarDtoList(cars.stream().map(CarMapper::toDto).toList());
    return carDtoList;
  }
}

