package com.example.autoverifier;

import lombok.extern.slf4j.Slf4j;
import org.javatuples.Pair;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

@Slf4j
public class YamlReader implements FilesReader {

    private final PathMatchingResourcePatternResolver resolver;

    public YamlReader() {
        resolver = new PathMatchingResourcePatternResolver();
    }

    public Stream<Pair<String, PairRequest>> get(String rootFolder) throws IOException {
        final var yaml = new Yaml();
        return Arrays.stream(resolver.getResources(String.format("/%s/*.yml", rootFolder)))
            .map(resource -> {
                try {
                    return Pair.with(resource.getFilename(),
                        yaml.<PairRequest>load(resource.getInputStream()));
                } catch (IOException e) {
                    log.error("Failed to load {}", resource.getFilename(), e);
                    return null;
                }
            }).filter(Objects::nonNull);
    }
}