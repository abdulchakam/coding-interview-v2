package com.abdulchakam.codinginterview.dog;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DogResponseDto {

    private String dogName;
    private String breed;
    private Map<String,List<String>> subBreeds;
}
