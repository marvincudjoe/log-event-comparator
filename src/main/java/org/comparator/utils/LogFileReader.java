package org.comparator.utils;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.comparator.model.EventDetails;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class LogFileReader {

    public static final String INVALID_PATH_FILE_PROVIDED = "Unable to locate file=%s. Input must be a valid absolute path";
    public static final String UNPROCESSABLE_FILE = "Unable to process logs in file={}";

    private LogFileReader() {}

    public static List<EventDetails> getEvents(@NonNull String filePath) throws FileNotFoundException {
        // file must be present
        if (!new File(filePath).isFile()) {
            throw new FileNotFoundException(String.format(INVALID_PATH_FILE_PROVIDED, filePath));
        }
        final List<EventDetails> events = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                PatternMatcher.extractAndMapEvent(line).ifPresent(events::add);
            }
            return events;
        } catch (IOException e) {
            log.error(UNPROCESSABLE_FILE, filePath, e);
        }
        return new ArrayList<>();
    }
}