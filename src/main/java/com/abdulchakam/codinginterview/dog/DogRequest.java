package com.abdulchakam.codinginterview.dog;

import com.fasterxml.jackson.annotation.JsonInclude;
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

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;

    @NotBlank(message = "Dog name is mandatory")
    private String dogName;

    @NotBlank(message = "Breed name is mandatory")
    private String breedName;

    private int getNumberOfImages;
}
