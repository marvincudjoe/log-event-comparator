package org.comparator.model;

import org.junit.jupiter.api.Test;

import java.time.DateTimeException;

import static org.comparator.TestUtils.*;
import static org.comparator.model.EventDetails.INVALID_DATE_TIME_FORMAT;
import static org.junit.jupiter.api.Assertions.*;

class EventDetailsTest {

    @Test
    void shouldCreateEventDetailsWithValidInputs() {
        EventDetails eventDetails = new EventDetails(EVENT_DATE_TIME_STR, EVENT_PID_STR, EVENT_DESCRIPTION);
        assertEquals(EVENT_DATE_TIME_STR, eventDetails.getDateTime().toString());
        assertEquals(Integer.parseInt(EVENT_PID_STR), eventDetails.getPid());
        assertEquals(EVENT_DESCRIPTION, eventDetails.getDescription());
    }

    @Test
    void shouldThrowExceptionForInvalidPid() {
        String invalidPid = "str";
        assertThrows(NumberFormatException.class, () -> new EventDetails(EVENT_DATE_TIME_STR, invalidPid, EVENT_DESCRIPTION));
    }

    @Test
    void shouldThrowExceptionForInvalidDateTime() {
        String dateTime = "2021-01-01T1";
        var err = assertThrows(DateTimeException.class, () -> new EventDetails(dateTime, EVENT_PID_STR, EVENT_DESCRIPTION));
        assertEquals(String.format(INVALID_DATE_TIME_FORMAT, dateTime), err.getMessage());
    }

    @Test
    void shouldReturnStringRepresentation() {
        EventDetails eventDetails = new EventDetails(EVENT_DATE_TIME_STR, EVENT_PID_STR, EVENT_DESCRIPTION);
        String expectedString = String.format("%s %s %s", EVENT_DATE_TIME_STR, EVENT_PID_STR, EVENT_DESCRIPTION);
        assertEquals(expectedString, eventDetails.toString());
    }
}
