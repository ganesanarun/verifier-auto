package com.example.autoverifier;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class AutoVerifierApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutoVerifierApplication.class, args).close();
    }

    @Bean
    RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build();
    }

    @Bean
    HttpClient requestMaker(RestTemplate restTemplate, ObjectMapper objectMapper) {
        return new HttpClient(restTemplate, objectMapper);
    }
    
    @Bean
    FilesReader testFilesReader() {
        return new YamlReader();
    }

    @Bean
    OutputProcessor outputProcessor() {
        return new JsonProcessor();
    }

    @Bean
    VerificationRunner verificationRunner(InputProcessor inputProcessor,
        FilesReader filesReader,
        OutputProcessor outputsProcessor) {
        return new VerificationRunner("templates",
            inputProcessor,
            filesReader,
            outputsProcessor);
    }

}

