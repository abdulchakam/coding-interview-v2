package com.abdulchakam.codinginterview.repository;

import com.abdulchakam.codinginterview.model.SubBreed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubBreedRepository extends JpaRepository<SubBreed, Long> {

    List<SubBreed> findByDogId(Long dogId);
    void deleteByDogId(Long dogId);
}
