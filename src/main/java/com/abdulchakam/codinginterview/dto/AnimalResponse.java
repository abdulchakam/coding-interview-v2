package com.abdulchakam.codinginterview.dto;

import com.abdulchakam.codinginterview.model.Dog;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnimalResponse {
    private List<Dog> dogList;
}
