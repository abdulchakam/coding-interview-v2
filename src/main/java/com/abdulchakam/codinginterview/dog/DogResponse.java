package com.abdulchakam.codinginterview.dog;

import com.abdulchakam.codinginterview.dto.BaseResponse;
import com.abdulchakam.codinginterview.model.Dog;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;


@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DogResponse extends BaseResponse {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private DogRequest request;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Dog> dogList;
}
