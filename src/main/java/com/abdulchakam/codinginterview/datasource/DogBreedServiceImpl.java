package com.abdulchakam.codinginterview.datasource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
@Slf4j
public class DogBreedServiceImpl implements DogBreedService{

    @Autowired
    private DogBreedFactory dogBreedFactory;

    @Value("${dog.api.base-url}")
    private String apiBaseUrl;

    @Override
    public DogBreedResponse getAllBreed() {
        DogBreedResponse dogBreedResponse = new DogBreedResponse();
        String urlGetAllBreeds = apiBaseUrl.concat("/breeds/list/all");

        RestTemplateFactory restTemplateFactory1 = new TimeoutRestTemplateFactory(5000);
        RestTemplate restTemplate1 = restTemplateFactory1.createRestTemplate();

        var response = restTemplate1.exchange(urlGetAllBreeds,
                HttpMethod.GET,
                null,
                DogBreedResponse.class);

        if (response.getBody() != null) {
            dogBreedResponse = dogBreedFactory.extractSubToParentBreed(response.getBody().getMessage());
        }

        return dogBreedResponse;
    }

    @Override
    public DogSubBreedResponse getSubBreed(String breed) {
        String urlSubBreed =  apiBaseUrl.concat("/breed/"+breed+"/list");

        RestTemplateFactory restTemplateFactory2 = new TimeoutRestTemplateFactory(2000);
        RestTemplate restTemplate2 = restTemplateFactory2.createRestTemplate();

        var response = restTemplate2.exchange(urlSubBreed,
                HttpMethod.GET,
                null,
                DogSubBreedResponse.class);

        return response.getBody();
    }
}
