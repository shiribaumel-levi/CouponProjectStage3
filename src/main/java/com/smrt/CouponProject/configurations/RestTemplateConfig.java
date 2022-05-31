package com.smrt.CouponProject.configurations;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;


@Configuration

public class RestTemplateConfig {
    /**
     * method that creates an instance of restTemplate.
     * @param builder an instance of restTemplateBuilder
     * @return an instance of restTemplate
     */
    @Bean

    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .setConnectTimeout(Duration.ofMillis(3000))
                .setReadTimeout(Duration.ofMillis(3000))
                .build();
    }
}
