package org.monitor.utils;

import lombok.extern.slf4j.Slf4j;
import org.monitor.model.EventDetails;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public final class PatternMatcher {
    private static final String REGEX = "([0-2]\\d:[0-5]\\d:[0-5]\\d\\.\\d+)\\s+(\\d+)\\s+((.+)$)";
    private static final Pattern PATTERN = Pattern.compile(REGEX);
    private static final String TIME_STAMP_PATTERN = "HH:mm:ss.SSS";

    public static EventDetails extractAndMapEvent(String event) {
        Matcher matcher = PATTERN.matcher(event);
        if (matcher.matches()) {
            final String timeStamp = matcher.group(1);
            final String pidStr = matcher.group(2);
            final String desc = matcher.group(3).trim();
            return mapToEventDetails(timeStamp, pidStr, desc);
        } else {
            log.debug("Unable to process event={}", event);
            return new EventDetails();
        }
    }

    private static EventDetails mapToEventDetails(String timeStampStr, String pidStr, String desc) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIME_STAMP_PATTERN);
        return EventDetails.builder().timeStamp(LocalTime.parse(timeStampStr, formatter)).pid(Integer.parseInt(pidStr)).description(desc).build();
    }
}
