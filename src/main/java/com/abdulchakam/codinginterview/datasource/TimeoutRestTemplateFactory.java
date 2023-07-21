package com.abdulchakam.codinginterview.datasource;

import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.util.Timeout;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class TimeoutRestTemplateFactory implements RestTemplateFactory {
    private final int connectionTimeout;

    public TimeoutRestTemplateFactory(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    @Override
    public RestTemplate createRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        RequestConfig requestConfig = RequestConfig.custom()
                        .setConnectTimeout(Timeout.ofMilliseconds(connectionTimeout)).setConnectionRequestTimeout(Timeout.ofMilliseconds(connectionTimeout)).build();
        factory.setHttpClient(HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build());

        restTemplate.setRequestFactory(factory);
        return restTemplate;
    }
}
