package com.example.autoverifier;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Map.Entry;

@AllArgsConstructor
public class HttpClient {

    final RestTemplate restTemplate;
    final ObjectMapper objectMapper;

    public JsonNode make(Request request, Map<String, String> headers) {
        final var httpHeaders = new HttpHeaders();
        for (Entry<String, String> entitySet : headers.entrySet()) {
            httpHeaders.add(entitySet.getKey(), entitySet.getValue());
        }
        return invoke(request, httpHeaders);
    }

    private JsonNode invoke(Request request, MultiValueMap<String, String> headers) {
        final var httpHeaders = new HttpHeaders(headers);
        HttpEntity<Object> body = new HttpEntity<>(objectMapper.convertValue(request.getBody(),
            JsonNode.class), headers);
        final var exchange = restTemplate.exchange(request.getUrl(),
            request.getMethod(),
            body,
            JsonNode.class);
        return exchange.getBody();
    }
}
