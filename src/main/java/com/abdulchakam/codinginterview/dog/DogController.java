package com.abdulchakam.codinginterview.dog;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DogController {

    @Autowired
    private DogService dogService;

    @PostMapping("/dog/store")
    public ResponseEntity<DogResponse> store(@Valid @RequestBody DogRequest dogRequest) {
        return dogService.dataFromRest(dogRequest);
    }

    @GetMapping("/dog/all")
    public ResponseEntity<DogResponse> showAll() {
        return dogService.showAll();
    }
}
