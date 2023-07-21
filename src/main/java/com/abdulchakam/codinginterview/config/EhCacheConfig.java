package com.abdulchakam.codinginterview.config;

import com.abdulchakam.codinginterview.datasource.DogBreedResponse;
import com.abdulchakam.codinginterview.datasource.DogSubBreedAndImagesResponse;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
@EnableCaching
public class EhCacheConfig {
    @Bean
    public org.ehcache.CacheManager ehCacheManager() {
        return CacheManagerBuilder.newCacheManagerBuilder()
                .withCache("allBreeds", CacheConfigurationBuilder.newCacheConfigurationBuilder(
                        Map.class, DogBreedResponse.class,
                        ResourcePoolsBuilder.heap(100))) // Adjust cache size as needed
                .withCache("subBreed", CacheConfigurationBuilder.newCacheConfigurationBuilder(
                        List.class, DogSubBreedAndImagesResponse.class,
                        ResourcePoolsBuilder.heap(100))) // Adjust cache size as needed
                .withCache("images", CacheConfigurationBuilder.newCacheConfigurationBuilder(
                        List.class, DogSubBreedAndImagesResponse.class,
                        ResourcePoolsBuilder.heap(500))) // Adjust cache size as needed
                .build(true);
    }
}
