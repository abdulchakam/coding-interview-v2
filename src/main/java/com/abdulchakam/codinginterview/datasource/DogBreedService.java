package com.abdulchakam.codinginterview.datasource;

public interface DogBreedService {

    DogBreedResponse getAllBreed();
    DogSubBreedAndImagesResponse getSubBreed(String breed);
    DogSubBreedAndImagesResponse getImage(String breed, String subBreed, int numberOfImages);
}
