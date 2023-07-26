package com.abdulchakam.codinginterview.dog;

import com.abdulchakam.codinginterview.dto.BaseResponse;
import com.abdulchakam.codinginterview.exception.BadRequestException;
import com.abdulchakam.codinginterview.exception.DataAlreadyExistException;
import com.abdulchakam.codinginterview.exception.DataNotFoundException;
import com.abdulchakam.codinginterview.model.Dog;
import com.abdulchakam.codinginterview.model.SubBreed;
import com.abdulchakam.codinginterview.repository.DogRepository;
import com.abdulchakam.codinginterview.repository.SubBreedRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class DogServiceTest {

    @Mock
    private DogFactory dogFactory;

    @Mock
    private DogRepository dogRepository;

    @Mock
    private SubBreedRepository subBreedRepository;

    @InjectMocks
    private DogService dogService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testCreateDog_expectSuccess() {
        // Mock dog request
        DogRequest dogRequest = new DogRequest();
        dogRequest.setDogName("dogName");
        dogRequest.setBreedName("breedName");
        dogRequest.setGetNumberOfImages(10);

        // Mock object dog
        Dog dog = new Dog();
        dog.setDogName("name");
        dog.setBreed("breed");
        dog.setCreatedBy("SYSTEM");
        dog.setCreatedDate(new Date());
        dog.setSubBreeds(List.of(SubBreed.builder()
                .subBreedName("sub breed 1")
                .images("images")
                .build()));

        when(dogFactory.fromRequest(dogRequest)).thenReturn(dog);
        when(dogRepository.save(any(Dog.class))).thenReturn(dog);

        ResponseEntity<DogResponse> responseEntity = dogService.storeDog(dogRequest);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("Success create dog", Objects.requireNonNull(responseEntity.getBody()).getMessage());
    }

    @Test
    public void testCreateDog_expectFailedDataAlreadyExist() {
        // Mock dog request
        DogRequest dogRequest = new DogRequest();
        dogRequest.setDogName("dogName");
        dogRequest.setBreedName("breedName");
        dogRequest.setGetNumberOfImages(10);

        // Mock object dog
        Dog dog = new Dog();
        dog.setId(1L);
        dog.setDogName("dogName");
        dog.setBreed("breed");
        dog.setCreatedBy("SYSTEM");
        dog.setCreatedDate(new Date());
        dog.setSubBreeds(List.of(SubBreed.builder()
                .subBreedName("sub breed 1")
                .images("images")
                .build()));

        when(dogRepository.findByDogName(dogRequest.getDogName())).thenReturn(Optional.of(dog));
        when(dogFactory.fromRequest(dogRequest)).thenReturn(dog);
        when(dogRepository.save(any(Dog.class))).thenReturn(dog);

        assertThrows(DataAlreadyExistException.class, () -> dogService.storeDog(dogRequest));
    }

    @Test
    public void testShowAll_expectSuccess() {
        Dog dog1 = new Dog();
        dog1.setDogName("name 1");
        dog1.setBreed("breed 1");
        dog1.setCreatedBy("SYSTEM");
        dog1.setCreatedDate(new Date());
        dog1.setModifiedBy("SYSTEM");
        dog1.setModifiedDate(new Date());
        dog1.setSubBreeds(List.of(SubBreed.builder()
                .subBreedName("sub breed 1")
                .images("[\"images1\",\"images2\",\"images3\",\"images4\",\"images5\"]")
                .build()));

        Dog dog2 = new Dog();
        dog2.setDogName("name 2");
        dog2.setBreed("breed 2");
        dog2.setCreatedBy("SYSTEM");
        dog2.setCreatedDate(new Date());
        dog1.setModifiedBy("SYSTEM");
        dog1.setModifiedDate(new Date());
        dog2.setSubBreeds(List.of(SubBreed.builder()
                .subBreedName("sub breed 2")
                .images("[\"images1\",\"images2\",\"images3\",\"images4\",\"images5\"]")
                .build()));

        when(dogRepository.findAll()).thenReturn(List.of(dog1, dog2));

        ResponseEntity<DogResponse> responseEntity = dogService.showAll();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Success show all dog", Objects.requireNonNull(responseEntity.getBody()).getMessage());

        List<DogResponseDto> dogResponseDtoList = responseEntity.getBody().getDogList();
        assertEquals(2, dogResponseDtoList.size());
    }

    @Test
    public void testUpdateDog_expectSuccess() {
        // Mock dog request
        DogRequest dogRequest = new DogRequest();
        dogRequest.setDogName("dogName");
        dogRequest.setBreedName("breedName");
        dogRequest.setGetNumberOfImages(10);

        // Mock object dog
        Dog dog = new Dog();
        dog.setDogName("name");
        dog.setBreed("breed");
        dog.setCreatedBy("SYSTEM");
        dog.setCreatedDate(new Date());
        dog.setModifiedBy("SYSTEM");
        dog.setModifiedDate(new Date());
        dog.setSubBreeds(List.of(SubBreed.builder()
                .subBreedName("sub breed 1")
                .images("images")
                .build()));

        SubBreed subBreed = new SubBreed();
        subBreed.setId(1L);
        subBreed.setDog(dog);
        subBreed.setSubBreedName("sub breed");
        subBreed.setImages("images");

        when(subBreedRepository.findByDogId(1L)).thenReturn(List.of(subBreed));

        when(dogFactory.fromRequest(dogRequest)).thenReturn(dog);
        when(dogRepository.findById(1L)).thenReturn(Optional.of(dog));
        when(dogRepository.save(any(Dog.class))).thenReturn(dog);

        ResponseEntity<DogResponse> responseEntity = dogService.updateDog(1L, dogRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Success update dog", Objects.requireNonNull(responseEntity.getBody()).getMessage());
    }

    @Test
    public void testUpdateDog_expectFailedDataAlreadyExist() {
        // Mock dog request
        DogRequest dogRequest = new DogRequest();
        dogRequest.setDogName("dogName");
        dogRequest.setBreedName("breedName");
        dogRequest.setGetNumberOfImages(10);

        // Mock object dog
        Dog dog = new Dog();
        dog.setId(2L);
        dog.setDogName("dogName");
        dog.setBreed("breedName");
        dog.setCreatedBy("SYSTEM");
        dog.setCreatedDate(new Date());
        dog.setModifiedBy("SYSTEM");
        dog.setModifiedDate(new Date());
        dog.setSubBreeds(List.of(SubBreed.builder()
                .subBreedName("sub breed 1")
                .images("images")
                .build()));

        when(dogRepository.findByDogName(dogRequest.getDogName())).thenReturn(Optional.of(dog));
        when(dogFactory.fromRequest(dogRequest)).thenReturn(dog);
        when(dogRepository.save(any(Dog.class))).thenReturn(dog);

        assertThrows(DataAlreadyExistException.class, ()-> dogService.updateDog(1L, dogRequest));
    }

    @Test
    public void testUpdateDog_expectFailedDataAlreadyExist2() {
        // Mock dog request
        DogRequest dogRequest = new DogRequest();
        dogRequest.setDogName("dogName");
        dogRequest.setBreedName("breedName");
        dogRequest.setGetNumberOfImages(10);

        // Mock object dog
        Dog dog = new Dog();
        dog.setDogName("dogName");
        dog.setBreed("breedName");
        dog.setCreatedBy("SYSTEM");
        dog.setCreatedDate(new Date());
        dog.setModifiedBy("SYSTEM");
        dog.setModifiedDate(new Date());
        dog.setSubBreeds(List.of(SubBreed.builder()
                .subBreedName("sub breed 1")
                .images("images")
                .build()));

        when(dogRepository.findByDogName(dogRequest.getDogName())).thenReturn(Optional.of(dog));
        when(dogFactory.fromRequest(dogRequest)).thenReturn(dog);
        when(dogRepository.save(any(Dog.class))).thenReturn(dog);

        assertThrows(DataAlreadyExistException.class, ()-> dogService.updateDog(1L, dogRequest));
    }

    @Test
    public void testUpdateDog_expectFailedOnlyOddNumber() {
        // Mock dog request
        DogRequest dogRequest = new DogRequest();
        dogRequest.setDogName("dog shiba");
        dogRequest.setBreedName("shiba");
        dogRequest.setGetNumberOfImages(10);

        // Mock object dog
        Dog dog = new Dog();
        dog.setId(1L);
        dog.setDogName("dogName");
        dog.setBreed("breedName");
        dog.setCreatedBy("SYSTEM");
        dog.setCreatedDate(new Date());
        dog.setModifiedBy("SYSTEM");
        dog.setModifiedDate(new Date());
        dog.setSubBreeds(List.of(SubBreed.builder()
                .subBreedName("sub breed 1")
                .images("images")
                .build()));

        when(dogRepository.findByDogName(dogRequest.getDogName())).thenReturn(Optional.of(dog));
        when(dogFactory.fromRequest(dogRequest)).thenReturn(dog);
        when(dogRepository.save(any(Dog.class))).thenReturn(dog);

        assertThrows(BadRequestException.class, ()-> dogService.updateDog(1L, dogRequest));
    }

    @Test
    public void testDeleteDog_expectSuccess() {

        // Mock object dog
        Dog dog = new Dog();
        dog.setDogName("name");
        dog.setBreed("breed");
        dog.setCreatedBy("SYSTEM");
        dog.setCreatedDate(new Date());
        dog.setSubBreeds(List.of(SubBreed.builder()
                .subBreedName("sub breed 1")
                .images("images")
                .build()));

        SubBreed subBreed = new SubBreed();
        subBreed.setId(1L);
        subBreed.setDog(dog);
        subBreed.setSubBreedName("sub breed");
        subBreed.setImages("images");

        when(subBreedRepository.findByDogId(1L)).thenReturn(List.of(subBreed));
        when(dogRepository.findById(1L)).thenReturn(Optional.of(dog));

        ResponseEntity<BaseResponse> responseEntity = dogService.deleteDog(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Success delete dog", Objects.requireNonNull(responseEntity.getBody()).getMessage());
    }

    @Test
    public void testDeleteDog_expectFailedDogNotFound() {
        when(dogRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, ()-> dogService.deleteDog(1L));
    }
}
