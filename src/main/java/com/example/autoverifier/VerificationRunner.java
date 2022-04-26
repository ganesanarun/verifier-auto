package com.example.autoverifier;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.javatuples.Pair;
import org.springframework.boot.CommandLineRunner;

import java.io.IOException;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class VerificationRunner implements CommandLineRunner {

    private final FilesReader filesReader;
    private final String rootFolder;
    private final InputProcessor inputProcessor;
    private final OutputProcessor outputsProcessor;

    VerificationRunner(String rootFolder,
        InputProcessor inputProcessor,
        FilesReader filesReader,
        OutputProcessor outputsProcessor) {
        this.rootFolder = rootFolder;
        this.inputProcessor = inputProcessor;
        this.filesReader = filesReader;
        this.outputsProcessor = outputsProcessor;
    }

    @Override
    public void run(String... args) throws IOException {
        final Stream<Pair<String, JsonNode>> results = filesReader.get(rootFolder)
            .map(fileNameWithBothRequests -> {
                var bothRequests = fileNameWithBothRequests.getValue1();
                final var diff = inputProcessor.process(bothRequests);
                return Pair.with(fileNameWithBothRequests.getValue0(), diff);
            });
        outputsProcessor.process(results.collect(Collectors.toList()));
    }
}

