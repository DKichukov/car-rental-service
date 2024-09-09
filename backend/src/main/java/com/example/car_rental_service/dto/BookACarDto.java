package com.example.car_rental_service.dto;

import com.example.car_rental_service.enums.BookCarStatus;
import java.util.Date;
import lombok.Data;

@Data
public class BookACarDto {
  private Integer id;
  private Date fromDate;
  private Date toDate;
  private Integer days;
  private Long price;
  private BookCarStatus bookCarStatus;
  private Integer userId;
  private Integer carId;
}
