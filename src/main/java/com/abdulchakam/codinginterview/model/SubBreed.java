package com.abdulchakam.codinginterview.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sub_breed")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubBreed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "dog_id")
    private Dog dog;

    @Column(name = "sub_breed_name")
    private String subBreedName;

    @Column(name = "images")
    private String images;
}
