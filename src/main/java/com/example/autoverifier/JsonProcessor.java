package com.example.autoverifier;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.javatuples.Pair;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.List;

@Slf4j
public class JsonProcessor implements OutputProcessor {

    public void process(List<Pair<String, JsonNode>> results) throws IOException {
        final var path = String.format("%s_%s", "report", Instant.now().toString());
        File pathAsFile = new File(path);

        if (!Files.exists(Paths.get(path)) && pathAsFile.mkdir()) {
            log.info("{} created", path);
            for (Pair<String, JsonNode> result : results) {
                final var fileNameWithoutExtension = result.getValue0().replace(".yml", "");

                if (result.getValue1() != null && result.getValue1().isEmpty()) {
                    log.info("{} responses matched", fileNameWithoutExtension);
                    continue;
                }

                final var outputFileName = fileNameWithoutExtension.concat(".json");
                final var file = new File(
                    pathAsFile.getAbsolutePath() + File.separator + outputFileName);
                var ignore = file.createNewFile();
                final var fileWriter = new FileWriter(file.getAbsolutePath());
                if (result.getValue1() != null) {
                    fileWriter.write(result.getValue1().toPrettyString());
                    log.error("{} response did not match {}", fileNameWithoutExtension,
                        result.getValue1().toString());
                } else {
                    fileWriter.write("{\"error\": \"Failed to generate report\"}");
                    log.error("{} Failed to generate report", fileNameWithoutExtension);
                }

                fileWriter.flush();
                fileWriter.close();
                log.info("{} reported", outputFileName);
            }
        }
    }
}