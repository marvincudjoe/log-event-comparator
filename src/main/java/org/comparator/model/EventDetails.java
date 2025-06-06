package org.comparator.model;

import lombok.NonNull;

import java.time.*;
import java.time.format.DateTimeFormatter;

public record EventDetails(OffsetDateTime dateTime, int pid, String description) {
    public static final String INVALID_DATE_TIME_FORMAT = "Invalid date time format=%s. Expected format is ISO_DATE_TIME 8601 (e.g., 2021-01-01T12:00:00Z)";

    public static EventDetails map(@NonNull String dateTime, @NonNull String pid, @NonNull String description) {
        final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        final OffsetDateTime parsedDate;
        try {
            parsedDate = OffsetDateTime.parse(dateTime, formatter);
        } catch (Exception e) {
            throw new DateTimeException(String.format(INVALID_DATE_TIME_FORMAT, dateTime), e);
        }
        if (!pid.matches("\\d+")) {
            throw new NumberFormatException("Invalid pid format=" + pid);
        }
        return new EventDetails(parsedDate, Integer.parseInt(pid), description);
    }

    @Override
    @NonNull
    public String toString() {
        return dateTime.toString() + " " + pid + " " + description;
    }
}
