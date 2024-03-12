package com.stepup.consumerservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration class for setting up and providing instances of the RestTemplate and MappingJackson2HttpMessageConverter
 * beans in a Spring application. These configurations are essential for handling HTTP requests and responses,
 * particularly in RESTful web service communication scenarios.
 *
 * @see org.springframework.context.annotation.Bean
 * @see org.springframework.web.client.RestTemplate
 * @see org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
 */
@Configuration
public class RestTemplateConfig {

    /**
     * Configures and returns an instance of the {@link org.springframework.web.client.RestTemplate} class.
     * This method adds a {@link org.springframework.http.converter.json.MappingJackson2HttpMessageConverter} to
     * the RestTemplate's message converters list.
     *
     * @return Configured instance of {@link org.springframework.web.client.RestTemplate}.
     */
    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter());
        return restTemplate;
    }

    /**
     * Creates and returns an instance of {@link org.springframework.http.converter.json.MappingJackson2HttpMessageConverter}.
     * This converter is responsible for converting JSON data to and from Java objects.
     *
     * @return Instance of {@link org.springframework.http.converter.json.MappingJackson2HttpMessageConverter}.
     */
    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        return new MappingJackson2HttpMessageConverter();
    }
}
