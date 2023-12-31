package com.abdulchakam.codinginterview.dog;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DogRequest {

    @NotBlank(message = "Dog name is mandatory")
    private String dogName;

    @NotBlank(message = "Breed name is mandatory")
    private String breedName;

    @Min(value = 1)
    private int getNumberOfImages;
}
