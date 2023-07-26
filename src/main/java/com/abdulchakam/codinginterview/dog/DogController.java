package com.abdulchakam.codinginterview.dog;

import com.abdulchakam.codinginterview.dto.BaseResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class DogController {

    @Autowired
    private DogService dogService;

    @PostMapping("/dog")
    public ResponseEntity<DogResponse> store(@Valid @RequestBody DogRequest request) {
        return dogService.storeDog(request);
    }

    @GetMapping("/dog")
    public ResponseEntity<DogResponse> showAll() {
        return dogService.showAll();
    }

    @PutMapping("/dog/{dogId}")
    public ResponseEntity<DogResponse> update(@PathVariable Long dogId,
                                              @RequestBody DogRequest request) {
        return dogService.updateDog(dogId, request);
    }

    @DeleteMapping("/dog/{dogId}")
    public ResponseEntity<BaseResponse> delete(@PathVariable Long dogId) {
        return dogService.deleteDog(dogId);
    }
}
