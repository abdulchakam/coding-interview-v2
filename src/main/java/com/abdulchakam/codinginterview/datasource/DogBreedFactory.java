package com.abdulchakam.codinginterview.datasource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class DogBreedFactory {

    public DogBreedResponse extractSubToParentBreed(Map<String, List<String>> listMap) {
        Map<String, List<String>> map = new HashMap<>();

        for (var data : listMap.entrySet()) {
            if (data.getKey().equals("sheepdog") || data.getKey().equals("terrier")) {
                String breed = data.getKey();
                List<String> subBreeds = data.getValue();

                for (String subBreed : subBreeds) {
                    String transformedKey = breed+"-"+subBreed;
                    map.put(transformedKey, new ArrayList<>());
                }

            } else {
                map.put(data.getKey(), data.getValue());
            }
        }

        Map<String, List<String>> mapSorted = new TreeMap<>(map);
        return DogBreedResponse.builder()
                .message(mapSorted)
                .status("success")
                .build();
    }
}
