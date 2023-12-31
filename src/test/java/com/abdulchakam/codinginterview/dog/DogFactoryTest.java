package com.abdulchakam.codinginterview.dog;

import com.abdulchakam.codinginterview.datasource.DogBreedResponse;
import com.abdulchakam.codinginterview.datasource.DogBreedService;
import com.abdulchakam.codinginterview.datasource.DogSubBreedAndImagesResponse;
import com.abdulchakam.codinginterview.model.Dog;
import com.abdulchakam.codinginterview.model.SubBreed;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DogFactoryTest {

    @Mock
    private DogBreedService dogBreedService;

    @InjectMocks
    private DogFactory dogFactory;

    @Test
    public void fromRequestTest() {
        // Mocking dogBreedService response
        DogBreedResponse dogBreedResponse = new DogBreedResponse();
        Map<String, List<String>> breedMap = new HashMap<>();
        breedMap.put("breed1", Arrays.asList("subBreed1", "subBreed2"));
        breedMap.put("breed2", Collections.singletonList("subBreed3"));
        dogBreedResponse.setMessage(breedMap);
        dogBreedResponse.setStatus("success");
        when(dogBreedService.getAllBreed()).thenReturn(dogBreedResponse);

        // Mocking dogBreedService response for subBreed
        DogSubBreedAndImagesResponse subBreedResponse = new DogSubBreedAndImagesResponse();
        subBreedResponse.setMessage(Arrays.asList("subBreed1", "subBreed2"));
        when(dogBreedService.getSubBreed("breed1")).thenReturn(subBreedResponse);

        DogSubBreedAndImagesResponse imagesResponse = new DogSubBreedAndImagesResponse();
        imagesResponse.setMessage(List.of("image1","images2","images3"));
        when(dogBreedService.getImage("breed1", "subBreed1", 3)).thenReturn(imagesResponse);
        when(dogBreedService.getImage("breed1", "subBreed2", 3)).thenReturn(imagesResponse);

        // Create a real instance of Gson
        Gson gson = new Gson();
        // Set Gson instance to DogFactory using ReflectionTestUtils
        ReflectionTestUtils.setField(dogFactory, "gson", gson);

        DogRequest dogRequest = new DogRequest();
        dogRequest.setDogName("dogName");
        dogRequest.setBreedName("breed1");
        dogRequest.setGetNumberOfImages(3);

        Dog result = dogFactory.fromRequest(dogRequest);

        assertEquals(dogRequest.getDogName(), result.getDogName());
        assertEquals(dogRequest.getBreedName(), result.getBreed());

        List<SubBreed> subBreeds = result.getSubBreeds();
        assertEquals(2, subBreeds.size());
        assertEquals("subBreed1", subBreeds.get(0).getSubBreedName());
        assertEquals("[\"image1\",\"images2\",\"images3\"]", subBreeds.get(0).getImages());
        assertEquals("subBreed2", subBreeds.get(1).getSubBreedName());
        assertEquals("[\"image1\",\"images2\",\"images3\"]", subBreeds.get(1).getImages());
    }
}
