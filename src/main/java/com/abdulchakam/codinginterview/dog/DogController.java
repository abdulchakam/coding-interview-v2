package com.abdulchakam.codinginterview.dog;

import com.abdulchakam.codinginterview.dto.BaseResponse;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class DogController {

    @Autowired
    private DogService dogService;

    @ApiOperation(value = "Create new dog")
    @PostMapping("/dog")
    public ResponseEntity<DogResponse> store(@Valid @RequestBody DogRequest request) {
        return dogService.storeDog(request);
    }

    @ApiOperation(value = "Get all dog list")
    @GetMapping("/dog")
    public ResponseEntity<DogResponse> showAll() {
        return dogService.showAll();
    }

    @ApiOperation(value = "Update dog")
    @PutMapping("/dog/{dogId}")
    public ResponseEntity<DogResponse> update(@RequestParam Long dogId,
                                              @RequestBody DogRequest request) {
        return dogService.updateDog(dogId, request);
    }

    @ApiOperation(value = "Delete dog")
    @DeleteMapping("/dog/{dogId}")
    public ResponseEntity<BaseResponse> delete(@RequestParam Long dogId) {
        return dogService.deleteDog(dogId);
    }
}
