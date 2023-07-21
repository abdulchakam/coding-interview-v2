package com.abdulchakam.codinginterview.datasource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DogBreedExtractor {

    public DogBreedResponse extractSubToParentBreed(Map<String, List<String>> listMap, boolean isTransform) {
        Map<String, List<String>> transformedMap = transformBreedMap(listMap, isTransform);
        Map<String, List<String>> mapSorted = new TreeMap<>(transformedMap);

        return DogBreedResponse.builder()
                .message(mapSorted)
                .status("success")
                .build();
    }

    private Map<String, List<String>> transformBreedMap(Map<String, List<String>> listMap, boolean isTransform) {
        Map<String, List<String>> transformedMap = new HashMap<>();

        for (var data : listMap.entrySet()) {
            String breed = data.getKey();
            List<String> subBreeds = data.getValue();

            if (isTransform && shouldTransformBreed(breed)) {
                for (String subBreed : subBreeds) {
                    String transformedKey = breed + "-" + subBreed;
                    transformedMap.put(transformedKey, new ArrayList<>());
                }
            } else {
                transformedMap.put(breed, subBreeds);
            }
        }

        return transformedMap;
    }

    private boolean shouldTransformBreed(String breed) {
        return breed.equals("sheepdog") || breed.equals("terrier");
    }
}
