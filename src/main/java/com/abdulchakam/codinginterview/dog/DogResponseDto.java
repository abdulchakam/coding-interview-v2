package com.abdulchakam.codinginterview.dog;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DogResponseDto {

    private String dogName;
    private String breed;
    private List<String> subBreeds;
}
