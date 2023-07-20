package com.abdulchakam.codinginterview.dto;

import com.abdulchakam.codinginterview.model.Dog;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnimalRequest {
    private Dog dog;
}
