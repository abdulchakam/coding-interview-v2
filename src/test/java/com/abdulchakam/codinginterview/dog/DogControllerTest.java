package com.abdulchakam.codinginterview.dog;

import com.abdulchakam.codinginterview.dto.BaseResponse;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class DogControllerTest {
    @Mock
    private DogService dogService;

    @InjectMocks
    private DogController dogController;

    private Validator validator;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testStore_expectSuccess() {
        DogRequest validRequest = new DogRequest();
        DogResponse expectedResponse = new DogResponse();

        when(dogService.dataFromRest(validRequest)).thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(expectedResponse));
        ResponseEntity<DogResponse> responseEntity = dogController.store(validRequest);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());
    }

    @Test
    public void testShowAll_expectSuccess() {
        // Mock
        DogResponseDto dogResponseDto = new DogResponseDto();
        dogResponseDto.setDogName("name");
        dogResponseDto.setBreed("breed");
        dogResponseDto.setId(1L);
        dogResponseDto.setImages(new ArrayList<>());
        dogResponseDto.setSubBreeds(Map.of("key",List.of("sub breed1", "sub breed2", "sub breed3")));

        DogResponse dogResponse = new DogResponse();
        dogResponse.setDogList(List.of(dogResponseDto));

        when(dogService.showAll()).thenReturn(ResponseEntity.status(HttpStatus.OK).body(dogResponse));
        ResponseEntity<DogResponse> responseEntity = dogController.showAll();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(dogResponse, responseEntity.getBody());
    }

    @Test
    public void testUpdate_expectSuccess() {
        DogRequest validRequest = new DogRequest();
        DogResponse expectedResponse = new DogResponse();

        when(dogService.updateDog(validRequest)).thenReturn(ResponseEntity.ok(expectedResponse));
        ResponseEntity<DogResponse> responseEntity = dogController.update(validRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());
    }

    @Test
    public void testDelete_expectSuccess() {
        DogRequest validRequest = new DogRequest();
        BaseResponse expectedResponse = new BaseResponse();

        when(dogService.deleteDog(validRequest)).thenReturn(ResponseEntity.ok(expectedResponse));
        ResponseEntity<BaseResponse> responseEntity = dogController.delete(validRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedResponse, responseEntity.getBody());
    }
}
