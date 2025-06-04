package org.comparator;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.comparator.model.EventDetails;

import java.time.Duration;
import java.util.*;

@Slf4j
public class ReportGenerator {
    public static final String JOB_TIME_DIFF_MS = "pid=%s took more than %s minutes. diff=%ss between start and end";
    /**
     * With a given list of EventDetails,
     * the list is sorted by pid then date if pid is known.
     * For each event, we store the START event then compare it to the END event (known if PID is a key in the map)
     *
     * @param events List of valid log events
     */
    public static Map<LogLevel, List<String>> generateReport(@NonNull final List<EventDetails> events) {

        // Sort by pid. If pid is known, sort by date
        List<EventDetails> eventsCopy = new ArrayList<>(events);
        eventsCopy.sort(Comparator.comparing(EventDetails::getPid).thenComparing(EventDetails::getDateTime));
        final Map<LogLevel, List<String>> report = new EnumMap<>(LogLevel.class);
        final long fiveMinInSecs = 5L * 60;
        final long tenMinInSecs = 10L * 60;
        final Map<Integer, EventDetails> startEvents = new HashMap<>();
        for (EventDetails event : eventsCopy) {
            if (startEvents.containsKey(event.getPid())) {
                // compare by time
                final var start = startEvents.get(event.getPid());
                final var diffInSecs = Duration.between(start.getDateTime(), event.getDateTime()).toSeconds();
                if (diffInSecs > tenMinInSecs) {
                    // event is marked as "error"
                    // when the time diff between event start and end difference is greater than 10
                    report.computeIfAbsent(LogLevel.ERROR, k -> new ArrayList<>())
                            .add(String.format(JOB_TIME_DIFF_MS, event.getPid(), 10, diffInSecs));
                } else if (diffInSecs > fiveMinInSecs && diffInSecs < tenMinInSecs) {
                    // event is marked as "warn"
                    // when the time diff between event start and end is longer than 5 minutes but less than 10 minutes
                    report.computeIfAbsent(LogLevel.WARN, k -> new ArrayList<>())
                            .add(String.format(JOB_TIME_DIFF_MS, event.getPid(), 5, diffInSecs));
                }
            } else {
                startEvents.put(event.getPid(), event);
            }
        }
        return report;
    }

    public static void publishToConsole(@NonNull Map<LogLevel, List<String>> report) {
        Optional.ofNullable(report.get(LogLevel.WARN)).ifPresent(errors -> errors.forEach(log::warn));
        Optional.ofNullable(report.get(LogLevel.ERROR)).ifPresent(errors -> errors.forEach(log::error));
    }

    public enum LogLevel {
        ERROR, WARN
    }
}
