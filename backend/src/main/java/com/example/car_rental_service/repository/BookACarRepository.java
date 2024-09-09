package com.example.car_rental_service.repository;

import com.example.car_rental_service.entity.BookACar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookACarRepository extends JpaRepository<BookACar, Integer> {
}
