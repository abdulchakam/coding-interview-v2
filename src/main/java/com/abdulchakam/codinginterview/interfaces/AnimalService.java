package com.abdulchakam.codinginterview.interfaces;

import com.abdulchakam.codinginterview.dto.AnimalRequest;
import com.abdulchakam.codinginterview.dto.AnimalResponse;

public interface AnimalService {
    void create(AnimalRequest animalRequest);
    void update(AnimalRequest animalRequest);
    void delete(AnimalRequest animalRequest);
    AnimalResponse all();
}
