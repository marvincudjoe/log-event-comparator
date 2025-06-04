package org.monitor.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.monitor.model.EventDetails;

import static org.monitor.TestUtils.*;

class PatternMatcherTest {

    @Test
    void extractAndMapEvent() {
        String wellFormedEventStr = EVENT_STAMP_STR + " " + EVENT_PID_STR + " " + EVENT_DESCRIPTION;
        var expected = new EventDetails(EVENT_STAMP_STR, EVENT_PID_STR, EVENT_DESCRIPTION);
        var res = PatternMatcher.extractAndMapEvent(wellFormedEventStr);
        Assertions.assertTrue(res.isPresent());
        Assertions.assertEquals(expected, res.get());
    }

    @Test
    void givenMalFormedEvent_returnEmptyResult() {
        String malFormedEventStr = EVENT_STAMP_STR + " " + EVENT_PID_STR;
        var res = PatternMatcher.extractAndMapEvent(malFormedEventStr);
        Assertions.assertTrue(res.isEmpty());
    }
}