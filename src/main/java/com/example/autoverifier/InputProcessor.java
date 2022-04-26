package com.example.autoverifier;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonpatch.diff.JsonDiff;
import org.springframework.stereotype.Component;

@Component
public class InputProcessor {

    private final APIKeys apiKeys;
    private final HttpClient requestMaker;

    public InputProcessor(APIKeys apiKeys, HttpClient requestMaker) {
        this.apiKeys = apiKeys;
        this.requestMaker = requestMaker;
    }

    JsonNode process(BothRequests bothRequests) {
        final var latestResponse = requestMaker.make(bothRequests.getLatest(),
            apiKeys.getLatest());
        final var legacyResponse = requestMaker.make(bothRequests.getLegacy(),
            apiKeys.getLegacy());
        return JsonDiff.asJson(legacyResponse, latestResponse);
    }
}
