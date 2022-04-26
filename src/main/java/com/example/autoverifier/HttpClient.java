package com.example.autoverifier;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;

@AllArgsConstructor
@Slf4j
public class HttpClient {

    final RestTemplate restTemplate;
    final ObjectMapper objectMapper;

    @Async
    public CompletableFuture<JsonNode> make(Request request, Map<String, String> headers) {
        log.info("Making a call for {}", request);
        final var httpHeaders = new HttpHeaders();
        for (Entry<String, String> entitySet : headers.entrySet()) {
            httpHeaders.add(entitySet.getKey(), entitySet.getValue());
        }
        return CompletableFuture.completedFuture(invoke(request, httpHeaders));
    }


    private JsonNode invoke(Request request, MultiValueMap<String, String> headers) {
        HttpEntity<Object> body = new HttpEntity<>(objectMapper.convertValue(request.getBody(),
            JsonNode.class), headers);
        final var exchange = restTemplate.exchange(request.getUrl(),
            request.getMethod(),
            body,
            JsonNode.class);
        return exchange.getBody();
    }
}
