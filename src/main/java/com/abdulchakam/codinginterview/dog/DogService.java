package com.abdulchakam.codinginterview.dog;

import com.abdulchakam.codinginterview.dto.AnimalRequest;
import com.abdulchakam.codinginterview.dto.AnimalResponse;
import com.abdulchakam.codinginterview.dto.BaseResponse;
import com.abdulchakam.codinginterview.exception.DataAlreadyExistException;
import com.abdulchakam.codinginterview.exception.DataNotFoundException;
import com.abdulchakam.codinginterview.interfaces.AnimalService;
import com.abdulchakam.codinginterview.model.Dog;
import com.abdulchakam.codinginterview.model.SubBreed;
import com.abdulchakam.codinginterview.repository.DogRepository;
import com.abdulchakam.codinginterview.repository.SubBreedRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ServerErrorException;

import java.util.*;

@Service
@Slf4j
public class DogService implements AnimalService {

    @Autowired
    private DogFactory dogFactory;

    @Autowired
    private DogRepository dogRepository;

    @Autowired
    private SubBreedRepository subBreedRepository;

    public ResponseEntity<DogResponse> dataFromRest(DogRequest dogRequest) {
        log.info("Start store dog");
        String statusMessage, message;
        int status;

        try {
            // Validate dog name
            Optional<Dog> dogOptional = dogRepository.findByDogName(dogRequest.getDogName());
            if (dogOptional.isPresent()) {
                throw new DataAlreadyExistException("Dog name already exist");
            }

            Dog dog = dogFactory.fromRequest(dogRequest);

            create(AnimalRequest.builder()
                    .dog(dog)
                    .build());

            statusMessage = HttpStatus.CREATED.getReasonPhrase();
            status = HttpStatus.CREATED.value();
            message = "Success create dog";

        } catch (ServerErrorException e) {
            statusMessage = HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
            status = HttpStatus.INTERNAL_SERVER_ERROR.value();
            message = "Error when create dog, "+ e.getMessage();
        }


        return ResponseEntity
                .status(status)
                .body(DogResponse.builder()
                        .status(status)
                        .statusMessage(statusMessage)
                        .message(message)
                        .request(dogRequest)
                        .build());
    }

    public ResponseEntity<DogResponse> showAll() {
        log.info("Start show all dog from database");
        String statusMessage, message;
        int status;
        AnimalResponse animalResponse = new AnimalResponse();
        List<DogResponseDto> dogResponseDtoList = new ArrayList<>();

        try {
            animalResponse = all();

            animalResponse.getDogList().forEach(dog -> {
                DogResponseDto dogResponseDto = new DogResponseDto();
                dogResponseDto.setBreed(dog.getBreed());
                dogResponseDto.setDogName(dog.getDogName());

                if (!dog.getSubBreeds().isEmpty()) {
                    Map<String, List<String>> listMap = new HashMap<>();
                    for (SubBreed subBreed : dog.getSubBreeds()){
                        listMap.put(subBreed.getSubBreedName(), new ArrayList<>());
                    }
                    dogResponseDto.setSubBreeds(listMap);
                }

                dogResponseDtoList.add(dogResponseDto);
            });

            statusMessage = HttpStatus.OK.getReasonPhrase();
            status = HttpStatus.OK.value();
            message = "Success show all dog";

        } catch (ServerErrorException e) {
            statusMessage = HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
            status = HttpStatus.INTERNAL_SERVER_ERROR.value();
            message = "Error when show all, "+ e.getMessage();
        }


        return ResponseEntity
                .status(status)
                .body(DogResponse.builder()
                        .status(status)
                        .statusMessage(statusMessage)
                        .message(message)
                        .dogList(dogResponseDtoList)
                        .build());
    }

    @Transactional
    public ResponseEntity<BaseResponse> deleteDog(DogRequest dogRequest) {
        log.info("Start delete dog");
        String statusMessage, message;
        int status;

        try {
            Dog dog = new Dog();
            dog.setId(dogRequest.getId());

            Optional<Dog> dogOpt = dogRepository.findById(dogRequest.getId());
            if (dogOpt.isEmpty()) {
                throw new DataNotFoundException("Data dog not found!");
            }

            delete(AnimalRequest
                    .builder()
                    .dog(dog)
                    .build());

            statusMessage = HttpStatus.OK.getReasonPhrase();
            status = HttpStatus.OK.value();
            message = "Success delete dog";

        } catch (ServerErrorException e) {
            statusMessage = HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
            status = HttpStatus.INTERNAL_SERVER_ERROR.value();
            message = "Error when delete dog, "+ e.getMessage();
        }

        return ResponseEntity
                .status(status)
                .body(DogResponse.builder()
                        .status(status)
                        .statusMessage(statusMessage)
                        .message(message)
                        .build());
    }

    @Override
    public void create(AnimalRequest animalRequest) {
        // Save dog breed
        Dog result = dogRepository.save(animalRequest.getDog());

        // Save sub breed
        List<SubBreed> subBreeds = new ArrayList<>();
        for (SubBreed subBreed : animalRequest.getDog().getSubBreeds()) {
            subBreed.setDog(result);
            subBreeds.add(subBreed);
        }
        subBreedRepository.saveAll(subBreeds);
    }


    @Override
    public AnimalResponse all() {
        List<Dog> dogList = dogRepository.findAll();
        return AnimalResponse.builder()
                .dogList(dogList)
                .build();
    }

    @Override
    public void update() {

    }

    @Override
    public void delete(AnimalRequest animalRequest) {
        dogRepository.deleteById(animalRequest.getDog().getId());
        if (!subBreedRepository.findByDogId(animalRequest.getDog().getId()).isEmpty()) {
            subBreedRepository.deleteByDogId(animalRequest.getDog().getId());
        }
    }
}
