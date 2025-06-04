package org.comparator.utils;

import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.comparator.model.EventDetails;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public final class PatternMatcher {
    private static final String REGEX = "([0-2]\\d:[0-5]\\d:[0-5]\\d\\.\\d+)\\s+(\\d+)\\s+((.+)$)";
    private static final Pattern PATTERN = Pattern.compile(REGEX);
    private static final String INCORRECT_LOG_FORMAT = "Unexpected format of event={}";

    private PatternMatcher() {}

    /**
     * Extracts events and maps to an Event object.
     *
     * <p>
     * given input "12:00:00.000 1000 job started" <br/>
     * with example regex "([0-2]\d:[0-5]\d:[0-5]\d\.\d+([+-][0-2]\d|))\s+(\d+)\s+((.+)$)"<br>
     * matcher group ["12:00:00.000","1000","job started"]
     * <p>
     * </p>
     *
     * @param event A well-formed log event
     * @return An EventDetails object
     */
    public static Optional<EventDetails> extractAndMapEvent(String event) {
        Matcher matcher = PATTERN.matcher(event);
        if (matcher.matches()) {
            final String timeStamp = matcher.group(1);
            final String pidStr = matcher.group(2);
            final String desc = matcher.group(3).trim();
            return Optional.of(new EventDetails(timeStamp, pidStr, desc));
        } else {
            log.debug(INCORRECT_LOG_FORMAT, event);
            return Optional.empty();
        }
    }
}
