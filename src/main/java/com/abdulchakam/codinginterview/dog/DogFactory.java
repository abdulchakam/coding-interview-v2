package com.abdulchakam.codinginterview.dog;

import com.abdulchakam.codinginterview.datasource.DogBreedResponse;
import com.abdulchakam.codinginterview.datasource.DogBreedServiceImpl;
import com.abdulchakam.codinginterview.datasource.DogSubBreedAndImagesResponse;
import com.abdulchakam.codinginterview.exception.DataNotFoundException;
import com.abdulchakam.codinginterview.model.Dog;
import com.abdulchakam.codinginterview.model.SubBreed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class DogFactory {

    @Autowired
    private DogBreedServiceImpl dogBreedService;

    public Dog fromRequest(DogRequest request) {

        // Get all data breed from data source
        DogBreedResponse dogBreedResponse = dogBreedService.getAllBreed();
        List<String> breeds = new ArrayList<>();
        List<SubBreed> subBreeds = new ArrayList<>();
        Dog dog = new Dog();

        for (var breed : dogBreedResponse.getMessage().entrySet()) {
            breeds.add(breed.getKey());
        }

        // Check breed name found or not
        boolean isFoundBreed, isFoundSubBreed;
        isFoundBreed = breeds.stream().anyMatch(breed -> breed.equals(request.getBreedName()));
        if (!isFoundBreed) {
            throw new DataNotFoundException("Breed is not found!");
        }

        // Check sub breed from data source
        DogSubBreedAndImagesResponse subBreedResponse = dogBreedService.getSubBreed(request.getBreedName());
        isFoundSubBreed = subBreedResponse.getMessage().isEmpty();

        // Block code if sub breed exist
        if (!isFoundSubBreed) {
            subBreedResponse.getMessage().forEach(s -> {
                SubBreed subBreed = new SubBreed();
                if (request.getBreedName().equals("sheepdog") || request.getBreedName().equals("terrier")) {
                    subBreed.setSubBreedName(request.getBreedName()+"-"+s);
                } else {
                    subBreed.setSubBreedName(s);
                }
                subBreeds.add(subBreed);
            });
        }

        dog.setDogName(request.getDogName());
        dog.setBreed(request.getBreedName());
        dog.setSubBreeds(subBreeds);
        dog.setCreatedBy("SYSTEM");
        dog.setCreatedDate(new Date());

        return dog;
    }
}
