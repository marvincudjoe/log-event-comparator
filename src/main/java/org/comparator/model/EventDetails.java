package org.comparator.model;

import lombok.Getter;
import lombok.NonNull;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Getter
public class EventDetails {
    public static final String INVALID_DATE_TIME_FORMAT = "Invalid date time format=%s. Expected format is ISO_DATE_TIME 8601 (e.g., 2021-01-01T12:00:00Z)";
    private final OffsetDateTime dateTime;
    private final int pid;
    private final String description;

    public EventDetails(@NonNull String dateTime, @NonNull String pid, @NonNull String description) {
        final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        try {
            this.dateTime = OffsetDateTime.parse(dateTime, formatter);
        } catch (Exception e) {
            throw new DateTimeException(String.format(INVALID_DATE_TIME_FORMAT, dateTime), e);
        }
        if (!pid.matches("\\d+")) {
            throw new NumberFormatException("Invalid pid format=" + pid);
        }
        this.pid = Integer.parseInt(pid);
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EventDetails eventDetails = (EventDetails) o;
        return eventDetails.dateTime.equals(dateTime) && eventDetails.pid == pid && eventDetails.description.equals(description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateTime, pid, description);
    }

    @Override
    public String toString() {
        return dateTime.toString() + " " + pid + " " + description;
    }
}
