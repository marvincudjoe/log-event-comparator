package org.comparator.utils;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.comparator.model.EventDetails;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
public class LogFileReader {

    public static final String INVALID_PATH_FILE_PROVIDED = "Unable to locate file=%s. Provided path must be a valid absolute path";
    public static final String UNPROCESSABLE_FILE = "Unable to process logs in file={}";

    private LogFileReader() {
    }

    public static List<EventDetails> getEvents(@NonNull String filePath) throws FileNotFoundException {
        var path = Path.of(filePath);
        // file must be present
        if (!Files.isRegularFile(path)) {
            throw new FileNotFoundException(String.format(INVALID_PATH_FILE_PROVIDED, filePath));
        }
        try (Stream<String> lines = Files.lines(path)) {
            return lines
                    .map(PatternMatcher::extractAndMapEvent)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .toList();
        } catch (IOException e) {
            log.error(UNPROCESSABLE_FILE, filePath, e);
        }
        return new ArrayList<>();
    }
}
