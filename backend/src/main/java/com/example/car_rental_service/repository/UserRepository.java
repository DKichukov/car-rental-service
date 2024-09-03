package com.example.car_rental_service.repository;

import com.example.car_rental_service.entity.User;
import com.example.car_rental_service.enums.UserRole;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    Optional<User> findFirstByEmail(String email);

    User findByUserRole(UserRole userRole);
}
