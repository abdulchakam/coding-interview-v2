package com.abdulchakam.codinginterview.repository;

import com.abdulchakam.codinginterview.model.Dog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DogRepository extends JpaRepository<Dog, Long> {

    Optional<Dog> findByDogName(String dogName);
}
