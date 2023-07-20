package com.abdulchakam.codinginterview.datasource;

public interface DogBreedService {

    DogBreedResponse getAllBreed();
    DogSubBreedResponse getSubBreed(String breed);
}
