package com.abdulchakam.codinginterview.datasource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DogSubBreedAndImagesResponse {
    List<String> message;
    String status;
}
