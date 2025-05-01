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
     * @param events List of valid log events
     */
    public static void generateReport(@NonNull List<EventDetails> events) {
        final String JOB_TIME_DIFF_MS = "pid={} took more than {} minutes. diff={}ms between start and end";

        // Sort by pid. If pid is known, sort by timestamp
        events.sort(Comparator.comparing(EventDetails::getPid).thenComparing(EventDetails::getTimeStamp));
        final long fiveMinInSecs = 5L * 60;
        final long tenMinInSecs = 10L * 60;
        Map<Integer, EventDetails> k = new HashMap<>();
        for (EventDetails event : events) {
            if (k.containsKey(event.getPid())) {
                // compare by time
                final var start = k.get(event.getPid());
                final var diffInSecs = Duration.between(start.getTimeStamp(), event.getTimeStamp()).toSeconds();

                if (diffInSecs > tenMinInSecs) {
                    // greater than 10 minutes
                    log.error(JOB_TIME_DIFF_MS, event.getPid(), 10, diffInSecs);
                } else if (diffInSecs > fiveMinInSecs && diffInSecs < tenMinInSecs) {
                    // longer than 5 minutes but less than 10 minutes
                    log.warn(JOB_TIME_DIFF_MS, event.getPid(), 5, diffInSecs);
                }
            } else {
                k.put(event.getPid(), event);
            }
        }
    }
}
