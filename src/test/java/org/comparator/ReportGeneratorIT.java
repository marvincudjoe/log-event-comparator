package org.comparator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.comparator.utils.LogFileReader;

import java.io.FileNotFoundException;

import static org.comparator.TestUtils.MALFORMED_EVENTS_FILE;
import static org.comparator.TestUtils.WELL_FORMED_EVENTS_FILE;

class ReportGeneratorIT {

    @Test
    void givenValidEvents_shouldGenerateReports() throws FileNotFoundException {
        var events = LogFileReader.getEvents(WELL_FORMED_EVENTS_FILE);
        Assertions.assertEquals(13, events.size());

        var report = ReportGenerator.generateReport(events);
        Assertions.assertEquals(2, report.size());
        Assertions.assertEquals(1, report.get(ReportGenerator.LogLevel.WARN).size());
        Assertions.assertEquals(3, report.get(ReportGenerator.LogLevel.ERROR).size());

        Assertions.assertDoesNotThrow(() -> ReportGenerator.publishToConsole(report));
    }

    @Test
    void givenEmptyEventsArray_shouldReturnEmptyReport() throws FileNotFoundException {
        var events = LogFileReader.getEvents(MALFORMED_EVENTS_FILE);
        Assertions.assertEquals(0, events.size());

        var report = ReportGenerator.generateReport(events);
        Assertions.assertEquals(0, report.size());
    }
}
