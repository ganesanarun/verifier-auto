package com.example.autoverifier;

import com.fasterxml.jackson.databind.JsonNode;
import org.javatuples.Pair;

import java.io.IOException;
import java.util.List;

@FunctionalInterface
public interface OutputProcessor {

    void process(List<Pair<String, JsonNode>> results) throws IOException;
}
