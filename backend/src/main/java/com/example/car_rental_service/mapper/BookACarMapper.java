package com.example.car_rental_service.mapper;

import com.example.car_rental_service.dto.BookACarDto;
import com.example.car_rental_service.entity.BookACar;

public final class BookACarMapper {

  private BookACarMapper() {
  }

  public static BookACarDto toDto(BookACar bookACar) {
    BookACarDto bookACarDto = new BookACarDto();
    bookACarDto.setId(bookACar.getId());
    bookACarDto.setDays(bookACar.getDays());
    bookACarDto.setBookCarStatus(bookACar.getBookCarStatus());
    bookACarDto.setPrice(bookACar.getPrice());
    bookACarDto.setToDate(bookACar.getToDate());
    bookACarDto.setFromDate(bookACar.getFromDate());
    bookACarDto.setEmail(bookACar.getUser().getEmail());
    bookACarDto.setUsername(bookACar.getUser().getUsername());
    bookACarDto.setUserId(bookACar.getUser().getId());
    bookACarDto.setCarId(bookACar.getCar().getId());
    return bookACarDto;
  }



}
