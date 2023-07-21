package com.abdulchakam.codinginterview.dog;

import com.abdulchakam.codinginterview.datasource.DogBreedResponse;
import com.abdulchakam.codinginterview.datasource.DogBreedService;
import com.abdulchakam.codinginterview.datasource.DogSubBreedAndImagesResponse;
import com.abdulchakam.codinginterview.exception.DataNotFoundException;
import com.abdulchakam.codinginterview.model.Dog;
import com.abdulchakam.codinginterview.model.SubBreed;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class DogFactory {

    private final DogBreedService dogBreedService;
    private final Gson gson;

    @Autowired
    public DogFactory(DogBreedService dogBreedService, Gson gson) {
        this.dogBreedService = dogBreedService;
        this.gson = gson;
    }

    public Dog fromRequest(DogRequest request) {
        DogBreedResponse dogBreedResponse = dogBreedService.getAllBreed();
        log.info("Response : {}", dogBreedResponse);
        Map<String, List<String>> breedMap = dogBreedResponse.getMessage();

        String breedName = request.getBreedName();
        if (!breedMap.containsKey(breedName)) {
            throw new DataNotFoundException("Breed is not found!");
        }

        DogSubBreedAndImagesResponse subBreedResponse = dogBreedService.getSubBreed(breedName);
        List<String> subBreedMap = subBreedResponse.getMessage();

        List<SubBreed> subBreeds = extractSubBreeds(breedName, subBreedMap, request.getGetNumberOfImages() );

        DogSubBreedAndImagesResponse imagesBreed = subBreedMap.isEmpty()
                ? dogBreedService.getImage(breedName, null, request.getGetNumberOfImages())
                : new DogSubBreedAndImagesResponse();

        return Dog.builder()
                .id(request.getId())
                .dogName(request.getDogName())
                .breed(breedName)
                .images(gson.toJson(imagesBreed.getMessage()))
                .subBreeds(subBreeds)
                .build();
    }

    private List<SubBreed> extractSubBreeds(String breedName, List<String> subBreedMap, int numberOfImages) {
        return subBreedMap.stream()
                .map(strings -> {
                    String subBreedName = (breedName.equals("sheepdog") || breedName.equals("terrier"))
                            ? breedName + "-" + strings : strings;
                    DogSubBreedAndImagesResponse imagesSubBreed = dogBreedService.getImage(breedName, strings, numberOfImages);

                    return SubBreed.builder()
                            .subBreedName(subBreedName)
                            .images(gson.toJson(imagesSubBreed.getMessage()))
                            .build();
                })
                .toList();
    }
}
