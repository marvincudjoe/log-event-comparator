package org.comparator.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.comparator.model.EventDetails;

import static org.comparator.TestUtils.*;

class PatternMatcherTest {

    @Test
    void extractAndMapEvent() {
        var expected = new EventDetails(EVENT_DATE_TIME_STR, EVENT_PID_STR, EVENT_DESCRIPTION);
        var res = PatternMatcher.extractAndMapEvent(expected.toString());
        Assertions.assertTrue(res.isPresent());
        Assertions.assertEquals(expected, res.get());
    }

    @Test
    void givenMalFormedEvent_returnEmptyResult() {
        String malFormedEventStr = EVENT_DATE_TIME_STR + " " + EVENT_PID_STR;
        var res = PatternMatcher.extractAndMapEvent(malFormedEventStr);
        Assertions.assertTrue(res.isEmpty());
    }
}
