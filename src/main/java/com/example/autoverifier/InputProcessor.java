package com.example.autoverifier;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonpatch.diff.JsonDiff;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class InputProcessor {

    private final APIKeys apiKeys;
    private final HttpClient requestMaker;

    public InputProcessor(APIKeys apiKeys, HttpClient requestMaker) {
        this.apiKeys = apiKeys;
        this.requestMaker = requestMaker;
    }

    JsonNode process(PairRequest bothRequests) {
        try {
            final var latestResponseCompletable = requestMaker.make(bothRequests.getLatest(),
                apiKeys.getLatest());
            final var legacyResponseCompletable = requestMaker.make(bothRequests.getLegacy(),
                apiKeys.getLegacy());
            return JsonDiff.asJson(legacyResponseCompletable.get(),
                latestResponseCompletable.get());
        } catch (Exception e) {
            log.error("Failed to make a call for {}", bothRequests, e);
            return null;
        }
    }
}
