package com.example.autoverifier;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.javatuples.Pair;
import org.springframework.stereotype.Component;

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
                final var fileName = result.getValue0()
                    .replace("yml", "json");
                final var file = new File(
                    pathAsFile.getAbsolutePath() + File.separator + fileName);
                var ignore = file.createNewFile();
                final var fileWriter = new FileWriter(file.getAbsolutePath());
                fileWriter.write(result.getValue1().toPrettyString());
                fileWriter.flush();
                fileWriter.close();
                log.info("{} reported", fileName);
            }
        }
    }
}