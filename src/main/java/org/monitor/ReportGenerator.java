package org.monitor;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.monitor.model.EventDetails;

import java.time.Duration;
import java.util.*;

@Slf4j
public class ReportGenerator {
    /**
     * With a given list of EventDetails,
     * the list is sorted by pid then timestamp if pid is known.
     * For each event, we store the START event then compare it to the END event (known if PID is a key in the map)
     *
     * @param events List of valid log events
     */
    public static Map<LogLevel, List<String>> generateReport(@NonNull List<EventDetails> events) {
        final String JOB_TIME_DIFF_MS = "pid=%s took more than %s minutes. diff=%sms between start and end";

        // Sort by pid. If pid is known, sort by timestamp
        events.sort(Comparator.comparing(EventDetails::getPid).thenComparing(EventDetails::getTimeStamp));
        final Map<LogLevel, List<String>> report = new HashMap<>();
        final List<String> errors = new ArrayList<>();
        final List<String> warnings = new ArrayList<>();
        final long fiveMinInSecs = 5L * 60;
        final long tenMinInSecs = 10L * 60;
        final Map<Integer, EventDetails> k = new HashMap<>();
        for (EventDetails event : events) {
            if (k.containsKey(event.getPid())) {
                // compare by time
                final var start = k.get(event.getPid());
                final var diffInSecs = Duration.between(start.getTimeStamp(), event.getTimeStamp()).toSeconds();

                if (diffInSecs > tenMinInSecs) {
                    // greater than 10
                    errors.add(String.format(JOB_TIME_DIFF_MS, event.getPid(), 10, diffInSecs));
                } else if (diffInSecs > fiveMinInSecs && diffInSecs < tenMinInSecs) {
                    // longer than 5 minutes but less than 10 minutes
                    warnings.add(String.format(JOB_TIME_DIFF_MS, event.getPid(), 5, diffInSecs));
                }
            } else {
                k.put(event.getPid(), event);
            }
        }
        report.put(LogLevel.ERROR, errors);
        report.put(LogLevel.WARN, warnings);
        return report;
    }

    public static void publishToConsole(@NonNull Map<LogLevel, List<String>> report) {
        report.get(LogLevel.WARN).forEach(log::warn);
        report.get(LogLevel.ERROR).forEach(log::error);
    }

    public enum LogLevel {
        ERROR,
        WARN
    }
}
