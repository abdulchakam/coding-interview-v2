package com.abdulchakam.codinginterview.datasource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DogBreedController {

    @Autowired
    private DogBreedServiceImpl dogBreedService;

    @GetMapping("/breeds/all")
    public DogBreedResponse getAllBreeds() {
        return dogBreedService.getAllBreed();
    }
}
