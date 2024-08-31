package com.example.car_rental_service.repository;

import com.example.car_rental_service.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    Optional<Object> findFirstByEmail(String email);
}
