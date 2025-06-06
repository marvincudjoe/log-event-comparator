package org.comparator.model;

import org.junit.jupiter.api.Test;

import java.time.DateTimeException;

import static org.comparator.TestUtils.*;
import static org.comparator.model.EventDetails.INVALID_DATE_TIME_FORMAT;
import static org.junit.jupiter.api.Assertions.*;

class EventDetailsTest {

    @Test
    void shouldCreateEventDetailsWithValidInputs() {
        EventDetails eventDetails = EventDetails.map(EVENT_DATE_TIME_STR, EVENT_PID_STR, EVENT_DESCRIPTION);
        assertEquals(EVENT_DATE_TIME_STR, eventDetails.dateTime().toString());
        assertEquals(Integer.parseInt(EVENT_PID_STR), eventDetails.pid());
        assertEquals(EVENT_DESCRIPTION, eventDetails.description());
    }

    @Test
    void shouldThrowExceptionForInvalidPid() {
        String invalidPid = "str";
        assertThrows(NumberFormatException.class, () -> EventDetails.map(EVENT_DATE_TIME_STR, invalidPid, EVENT_DESCRIPTION));
    }

    @Test
    void shouldThrowExceptionForInvalidDateTime() {
        String dateTime = "2021-01-01T1";
        var err = assertThrows(DateTimeException.class, () -> EventDetails.map(dateTime, EVENT_PID_STR, EVENT_DESCRIPTION));
        assertEquals(String.format(INVALID_DATE_TIME_FORMAT, dateTime), err.getMessage());
    }

    @Test
    void shouldReturnStringRepresentation() {
        EventDetails eventDetails = EventDetails.map(EVENT_DATE_TIME_STR, EVENT_PID_STR, EVENT_DESCRIPTION);
        String expectedString = String.format("%s %s %s", EVENT_DATE_TIME_STR, EVENT_PID_STR, EVENT_DESCRIPTION);
        assertEquals(expectedString, eventDetails.toString());
    }
}
