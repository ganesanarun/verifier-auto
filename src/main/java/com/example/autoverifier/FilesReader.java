package com.example.autoverifier;

import org.javatuples.Pair;

import java.io.IOException;
import java.util.stream.Stream;

public interface FilesReader {

    Stream<Pair<String, PairRequest>> get(String rootFolder) throws IOException;
}
