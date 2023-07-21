package com.abdulchakam.codinginterview.datasource;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DogBreedControllerTest {

    @Mock
    private DogBreedServiceImpl dogBreedService;

    @InjectMocks
    private DogBreedController dogBreedController;


    @Test
    public void testGetAllBreeds_expectSuccess() {
        DogBreedResponse dogBreedResponse = new DogBreedResponse();
        Map<String, List<String>> breedMap = new HashMap<>();
        breedMap.put("breed1", List.of("subBreed1", "subBreed2"));
        breedMap.put("breed2", List.of("subBreed3"));
        dogBreedResponse.setMessage(breedMap);
        dogBreedResponse.setStatus("success");

        when(dogBreedService.getAllBreed()).thenReturn(dogBreedResponse);
        DogBreedResponse response = dogBreedController.getAllBreeds();

        assertEquals(dogBreedResponse.getStatus(), response.getStatus());
        assertEquals(dogBreedResponse.getMessage(), response.getMessage());
    }
}
