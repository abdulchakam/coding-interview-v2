package com.abdulchakam.codinginterview.datasource;

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
public class DogBreedResponse {
    private Map<String, List<String>> message;
    private String status;
}
