package com.abdulchakam.codinginterview.datasource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
public class DogBreedServiceTest {
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private DogBreedServiceImpl dogBreedService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllBreed_expectSuccess() throws IllegalAccessException, NoSuchFieldException {
        Map<String, List<String>> listMap = new HashMap<>();
        listMap.put("key", List.of("value"));

        DogBreedResponse expectedResponse = new DogBreedResponse();
        expectedResponse.setStatus("success");
        expectedResponse.setMessage(listMap);

        Field databaseNameField = DogBreedServiceImpl.class.getDeclaredField("apiBaseUrl");
        databaseNameField.setAccessible(true);
        databaseNameField.set(dogBreedService, "https://dog.ceo/api");

        when(restTemplate.exchange(anyString(),
                eq(HttpMethod.GET),
                eq(null),
                eq(DogBreedResponse.class)))
                .thenReturn(new ResponseEntity<>(expectedResponse, HttpStatus.OK));
        DogBreedResponse actualResponse = dogBreedService.getAllBreed();

        assertNotNull(actualResponse);
    }

    @Test
    public void testGetAllBreed_expectConnectionTimeout() throws NoSuchFieldException, IllegalAccessException {
        Field databaseNameField = DogBreedServiceImpl.class.getDeclaredField("apiBaseUrl");
        databaseNameField.setAccessible(true);
        databaseNameField.set(dogBreedService, "https://dog.ceo/api");

        when(restTemplate.exchange(anyString(),
                eq(HttpMethod.GET),
                eq(null),
                eq(DogBreedResponse.class)))
                .thenThrow(new ResourceAccessException("Connection timeout"));

        try {
            dogBreedService.getAllBreed();
        } catch (ResourceAccessException e) {
            assertEquals("Connection timeout", e.getMessage());
        }
    }

    @Test
    public void testGetSubBreed_expectSuccess() throws IllegalAccessException, NoSuchFieldException {
        DogSubBreedAndImagesResponse expectedResponse = new DogSubBreedAndImagesResponse();
        expectedResponse.setStatus("success");
        expectedResponse.setMessage(List.of("subBreed1","subBreed2"));

        Field databaseNameField = DogBreedServiceImpl.class.getDeclaredField("apiBaseUrl");
        databaseNameField.setAccessible(true);
        databaseNameField.set(dogBreedService, "https://dog.ceo/api");

        when(restTemplate.exchange(anyString(),
                eq(HttpMethod.GET),
                eq(null),
                eq(DogSubBreedAndImagesResponse.class)))
                .thenReturn(new ResponseEntity<>(expectedResponse, HttpStatus.OK));
        DogSubBreedAndImagesResponse actualResponse = dogBreedService.getSubBreed("sheepdog");

        assertNotNull(actualResponse);
    }

    @Test
    public void testGetSubBreed_expectConnectionTimeout() throws NoSuchFieldException, IllegalAccessException {
        Field databaseNameField = DogBreedServiceImpl.class.getDeclaredField("apiBaseUrl");
        databaseNameField.setAccessible(true);
        databaseNameField.set(dogBreedService, "https://dog.ceo/api");

        when(restTemplate.exchange(anyString(),
                eq(HttpMethod.GET),
                eq(null),
                eq(DogSubBreedAndImagesResponse.class)))
                .thenThrow(new ResourceAccessException("Connection timeout"));

        try {
            dogBreedService.getSubBreed("sheepdog");
        } catch (ResourceAccessException e) {
            assertEquals("Connection timeout", e.getMessage());
        }
    }


    @Test
    public void testGetImage_expectSuccess() throws IllegalAccessException, NoSuchFieldException {
        DogSubBreedAndImagesResponse expectedResponse = new DogSubBreedAndImagesResponse();
        expectedResponse.setStatus("success");
        expectedResponse.setMessage(List.of("image1","image2"));

        Field databaseNameField = DogBreedServiceImpl.class.getDeclaredField("apiBaseUrl");
        databaseNameField.setAccessible(true);
        databaseNameField.set(dogBreedService, "https://dog.ceo/api");

        when(restTemplate.exchange(anyString(),
                eq(HttpMethod.GET),
                eq(null),
                eq(DogSubBreedAndImagesResponse.class)))
                .thenReturn(new ResponseEntity<>(expectedResponse, HttpStatus.OK));
        DogSubBreedAndImagesResponse actualResponse = dogBreedService.getImage("sheepdog",null,3);

        assertNotNull(actualResponse);
    }

    @Test
    public void testGetImage_expectSuccess2() throws IllegalAccessException, NoSuchFieldException {
        DogSubBreedAndImagesResponse expectedResponse = new DogSubBreedAndImagesResponse();
        expectedResponse.setStatus("success");
        expectedResponse.setMessage(List.of("image1","image2"));

        Field databaseNameField = DogBreedServiceImpl.class.getDeclaredField("apiBaseUrl");
        databaseNameField.setAccessible(true);
        databaseNameField.set(dogBreedService, "https://dog.ceo/api");

        when(restTemplate.exchange(anyString(),
                eq(HttpMethod.GET),
                eq(null),
                eq(DogSubBreedAndImagesResponse.class)))
                .thenReturn(new ResponseEntity<>(expectedResponse, HttpStatus.OK));
        DogSubBreedAndImagesResponse actualResponse = dogBreedService.getImage("sheepdog","english",3);

        assertNotNull(actualResponse);
    }

    @Test
    public void testGetImage_expectConnectionTimeout() throws NoSuchFieldException, IllegalAccessException {
        Field databaseNameField = DogBreedServiceImpl.class.getDeclaredField("apiBaseUrl");
        databaseNameField.setAccessible(true);
        databaseNameField.set(dogBreedService, "https://dog.ceo/api");

        when(restTemplate.exchange(anyString(),
                eq(HttpMethod.GET),
                eq(null),
                eq(DogSubBreedAndImagesResponse.class)))
                .thenThrow(new ResourceAccessException("Connection timeout"));

        try {
            dogBreedService.getImage("sheepdog", null, 3);
        } catch (ResourceAccessException e) {
            assertEquals("Connection timeout", e.getMessage());
        }
    }
}
