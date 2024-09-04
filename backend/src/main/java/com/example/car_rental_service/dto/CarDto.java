package com.example.car_rental_service.dto;

import java.util.Date;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CarDto {

  private Integer id;
  private String brand;
  private String color;
  private String name;
  private String type;
  private String transmission;
  private String description;
  private Long price;
  private Date year;
  private MultipartFile image;
  private byte[] returnedImage;
}
