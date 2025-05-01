package org.monitor.utils;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.monitor.model.EventDetails;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class LogFileReader {
    public static List<EventDetails> getEvents(@NonNull String filePath) throws FileNotFoundException {
        // file must be present
        if (!new File(filePath).isFile()) {
            throw new FileNotFoundException(String.format("Unable to locate file=%s. Input must be a valid absolute path", filePath));
        }
        final List<EventDetails> events = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                final EventDetails event = PatternMatcher.extractAndMapEvent(line);
                if (event != null) {
                    events.add(event);
                }
            }
            return events;
        } catch (IOException e) {
            log.error("Unable to process logs in file={}", filePath, e);
        }
        return new ArrayList<>();
    }

}
