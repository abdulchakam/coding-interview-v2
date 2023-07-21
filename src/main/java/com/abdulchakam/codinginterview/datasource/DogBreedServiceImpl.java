package com.abdulchakam.codinginterview.datasource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.Instant;


@Service
@Slf4j
public class DogBreedServiceImpl implements DogBreedService{

    @Autowired
    private RestTemplate restTemplate;

    @Value("${dog.api.base-url}")
    private String apiBaseUrl;

    @Override
    public DogBreedResponse getAllBreed() {
        log.info("get all breed from data source");
        try {
            DogBreedResponse dogBreedResponse = new DogBreedResponse();
            String urlGetAllBreeds = apiBaseUrl.concat("/breeds/list/all");

            RestTemplateFactory restTemplateFactory1 = new TimeoutRestTemplateFactory(5000);
            RestTemplate restTemplate1 = restTemplateFactory1.createRestTemplate();

            Instant startTime = Instant.now();
            var response = restTemplate1.exchange(urlGetAllBreeds,
                    HttpMethod.GET,
                    null,
                    DogBreedResponse.class);

            Instant endTime = Instant.now();
            Duration requestTime = Duration.between(startTime, endTime);

            if (response.getBody() != null) {
                dogBreedResponse = response.getBody();
            }

            log.info("Success get all breed : {}", requestTime.toMillis());
            return dogBreedResponse;
        } catch (ResourceAccessException e) {
            log.error("Connection Timeout : {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public DogSubBreedAndImagesResponse getSubBreed(String breed) {
        log.info("get sub breed from data source");
        try {
            String urlSubBreed =  apiBaseUrl.concat("/breed/"+breed+"/list");

            RestTemplateFactory restTemplateFactory2 = new TimeoutRestTemplateFactory(2000);
            RestTemplate restTemplate2 = restTemplateFactory2.createRestTemplate();

            Instant startTime = Instant.now();
            var response = restTemplate2.exchange(urlSubBreed,
                    HttpMethod.GET,
                    null,
                    DogSubBreedAndImagesResponse.class);
            Instant endTime = Instant.now();
            Duration requestTime = Duration.between(startTime, endTime);

            log.info("Success get sub breed : {}", requestTime.toMillis());
            return response.getBody();
        } catch (ResourceAccessException e) {
            log.error("Connection timeout : {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public DogSubBreedAndImagesResponse getImage(String breed, String subBreed, int numberOfImages) {
        log.info("get image from data source");
        try {
            String url = urlGetImages(breed, subBreed, numberOfImages);
            var response = restTemplate.exchange(url,
                    HttpMethod.GET,
                    null,
                    DogSubBreedAndImagesResponse.class);

            return response.getBody();
        } catch (ResourceAccessException e) {
            log.error("Connection timeout : {}", e.getMessage());
            throw e;
        }
    }

    private String urlGetImages(String breed, String subBreed, int numberOfImages) {
        String url;
        if (!ObjectUtils.isEmpty(subBreed)) {
            url =  apiBaseUrl.concat("/breed/"+breed+"/"+subBreed+"/images/random/"+numberOfImages);
        } else {
            url =  apiBaseUrl.concat("/breed/"+breed+"/images/random/"+numberOfImages);
        }

        return url;
    }
}
