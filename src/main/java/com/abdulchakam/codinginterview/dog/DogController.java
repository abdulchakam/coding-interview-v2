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
        return dogService.dataFromRest(request);
    }

    @GetMapping("/dog/all")
    public ResponseEntity<DogResponse> showAll() {
        return dogService.showAll();
    }

    @PutMapping("/dog")
    public ResponseEntity<DogResponse> update(@RequestBody DogRequest request) {
        return dogService.updateDog(request);
    }

    @DeleteMapping("/dog")
    public ResponseEntity<BaseResponse> delete(@RequestBody DogRequest request) {
        return dogService.deleteDog(request);
    }
}
