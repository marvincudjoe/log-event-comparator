package org.comparator.model;


import lombok.Getter;
import lombok.NonNull;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Getter
public class EventDetails {
    private static final String TIME_STAMP_PATTERN = "HH:mm:ss.SSS";

    private final LocalTime timeStamp;
    private final int pid;
    private final String description;

    public EventDetails(@NonNull String timeStamp, @NonNull String pid, @NonNull String description) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIME_STAMP_PATTERN);
        this.timeStamp = LocalTime.parse(timeStamp.strip(), formatter);
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
        EventDetails eventDetails = (EventDetails) o;
        return eventDetails.timeStamp.equals(timeStamp) && eventDetails.pid == pid && eventDetails.description.equals(description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timeStamp, pid, description);
    }

    @Override
    public String toString() {
        return String.format(timeStamp.toString(), pid, description);
    }

}
