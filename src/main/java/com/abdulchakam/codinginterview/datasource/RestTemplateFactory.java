package com.abdulchakam.codinginterview.datasource;

import org.springframework.web.client.RestTemplate;

public interface RestTemplateFactory {
    RestTemplate createRestTemplate();
}
