package org.comparator.utils;

import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.comparator.model.EventDetails;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public final class PatternMatcher {
    private static final String REGEX = "(\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}(Z|[+\\-]\\d{2}:\\d{2}))\\s+(\\d+)\\s+(.+)$";
    private static final Pattern PATTERN = Pattern.compile(REGEX);
    private static final String INCORRECT_LOG_FORMAT = "Unexpected format of event={}";

    private PatternMatcher() {
    }

    /**
     * Extracts events and maps to an Event object.
     *
     * <p>
     * given input "2021-01-01T11:50:59.000Z 998 job ended" <br/>
     * with example regex "(\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}\.\d{3}(Z|[+\-]\d{2}:\d{2}))\s+(\d+)\s+((.+)$)"<br>
     * matcher group ["2021-01-01T11:50:59.000Z", "Z", "1000","job started"]
     * </p>
     *
     * @param event A well-formed log event
     * @return An EventDetails object
     */
    public static Optional<EventDetails> extractAndMapEvent(String event) {
        Matcher matcher = PATTERN.matcher(event);
        if (matcher.matches()) {
            final String dateTime = matcher.group(1);
            final String pidStr = matcher.group(3);
            final String desc = matcher.group(4).trim();
            return Optional.of(new EventDetails(dateTime, pidStr, desc));
        } else {
            log.debug(INCORRECT_LOG_FORMAT, event);
            return Optional.empty();
        }
    }
}
