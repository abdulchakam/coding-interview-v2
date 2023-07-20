package com.abdulchakam.codinginterview.repository;

import com.abdulchakam.codinginterview.model.SubBreed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubBreedRepository extends JpaRepository<SubBreed, Long> {
}
