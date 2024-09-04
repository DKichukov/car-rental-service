package com.example.car_rental_service.entity;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.Data;

@Data
@Entity
@Table(name = "cars")
public class Car {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Integer id;
  private String brand;
  private String color;
  private String name;
  private String type;
  private String transmission;
  private String description;
  private Long price;
  private Date year;
  @Column(columnDefinition = "longblob")
  private byte[] image;

}
