package com.abdulchakam.codinginterview.datasource;

public interface DogBreedService {

    DogBreedResponse getAllBreed();
    DogSubBreedAndImagesResponse getSubBreed(String breed);
    DogSubBreedAndImagesResponse getImageByBreed(String breed);
    DogSubBreedAndImagesResponse getImageBySubBreed(String breed, String subBreed);
}
