package org.comparator;

import java.time.Duration;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.comparator.model.EventDetails;

import static org.comparator.ReportGenerator.JOB_TIME_DIFF_MS;
import static org.comparator.TestUtils.*;

class ReportGeneratorTest {

    @Test
    void givenValidEvents_shouldGenerateReport() {
        var eventStart = EventDetails.map("2021-01-01T12:00:00.000Z", "1000", EVENT_DESCRIPTION);
        var eventEnd = EventDetails.map("2021-01-01T12:40:07.000Z", "1000", "job took more than 10 minutes");
        var events = List.of(
                eventStart,
                eventEnd,
                EventDetails.map("2021-01-01T12:00:06.000Z", "1004", EVENT_DESCRIPTION),
                EventDetails.map("2021-01-01T12:05:20.000Z", "1004", "job took more than 5 minutes")
        );
        var report = ReportGenerator.generateReport(events);
        Assertions.assertEquals(2, report.size());
        Assertions.assertEquals(1, report.get(ReportGenerator.LogLevel.WARN).size());
        Assertions.assertEquals(1, report.get(ReportGenerator.LogLevel.ERROR).size());

        List<String> errors = report.get(ReportGenerator.LogLevel.ERROR);
        var expectedEntry = String.format(JOB_TIME_DIFF_MS, eventStart.pid(), 10, Duration.between(eventStart.dateTime(), eventEnd.dateTime()).toSeconds());
        Assertions.assertLinesMatch(List.of(expectedEntry), errors);

        Assertions.assertDoesNotThrow(() -> ReportGenerator.publishToConsole(report));
    }

    @Test
    void givenValidWarnEvents_shouldGenerateReport() {
        var eventStart = EventDetails.map("2021-01-01T12:00:06.000Z", "1004", EVENT_DESCRIPTION);
        var eventEnd = EventDetails.map("2021-01-01T12:05:20.000Z", "1004", "job took more than 5 minutes");
        var events = List.of(eventStart, eventEnd);
        var report = ReportGenerator.generateReport(events);
        Assertions.assertEquals(1, report.size());
        Assertions.assertEquals(1, report.get(ReportGenerator.LogLevel.WARN).size());

        List<String> warnings = report.get(ReportGenerator.LogLevel.WARN);
        var expectedEntry = String.format(JOB_TIME_DIFF_MS, eventStart.pid(), 5, Duration.between(eventStart.dateTime(), eventEnd.dateTime()).toSeconds());
        Assertions.assertLinesMatch(List.of(expectedEntry), warnings);

        Assertions.assertDoesNotThrow(() -> ReportGenerator.publishToConsole(report));
    }

    @Test
    void givenEventsWithNoEnd_returnEmptyReport() {
        var events = List.of(
                EventDetails.map("2021-01-01T12:00:00.000Z", "1000", EVENT_DESCRIPTION),
                EventDetails.map("2021-01-01T12:40:07.000Z", "1001", EVENT_DESCRIPTION),
                EventDetails.map("2021-01-01T12:00:06.000Z", "1002", EVENT_DESCRIPTION),
                EventDetails.map("2021-01-01T12:05:20.000Z", "1003", EVENT_DESCRIPTION)
        );
        var res = ReportGenerator.generateReport(events);
        Assertions.assertEquals(0, res.size());
    }
}