package com.abdulchakam.codinginterview.dog;

import com.fasterxml.jackson.annotation.JsonInclude;
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

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String,List<String>> subBreeds;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> images;
}
