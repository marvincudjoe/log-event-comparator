package org.comparator;

import lombok.extern.slf4j.Slf4j;
import org.comparator.model.EventDetails;
import org.comparator.utils.LogFileReader;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
public class Main {
    private static final String ARG_SEPARATOR = "=";
    private static final String FILE_ARG_NOT_PRESENT = "Expected at least: --file=<path.log>";

    public static void main(String[] args) throws FileNotFoundException {
        if (args.length == 0 || args[0].trim().isBlank()) {
            throw new IllegalArgumentException(FILE_ARG_NOT_PRESENT);
        }
        // validate arguments
        var filePath = Arrays.stream(args)
                .findAny()
                .filter(arg -> arg.startsWith("--file=") && arg.endsWith(".log"))
                .orElseThrow(() -> new NoSuchElementException(FILE_ARG_NOT_PRESENT))
                .split(ARG_SEPARATOR)[1];

        // read events
        List<EventDetails> events = LogFileReader.getEvents(filePath);
        // compare logs and generate report
        final var report = ReportGenerator.generateReport(events);
        // publish report to console
        ReportGenerator.publishToConsole(report);
    }
}
