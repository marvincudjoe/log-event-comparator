package org.comparator;

import lombok.extern.slf4j.Slf4j;
import org.comparator.model.EventDetails;
import org.comparator.utils.LogFileReader;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

@Slf4j
public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(System.in);
        log.info("Insert absolute path to file");
        String fileName = in.nextLine();
        // read events
        List<EventDetails> events = LogFileReader.getEvents(fileName);
        // compare logs and generate report
        final var report = ReportGenerator.generateReport(events);
        // publish report to console
        ReportGenerator.publishToConsole(report);
    }
}
