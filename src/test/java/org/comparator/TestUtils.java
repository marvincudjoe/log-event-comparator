package org.comparator;

import java.io.File;

public class TestUtils {
    public static final String WELL_FORMED_EVENTS_FILE = new File("src/test/resources/well-formed-events.log").getAbsolutePath();
    public static final String MALFORMED_EVENTS_FILE = new File("src/test/resources/malformed-events.log").getAbsolutePath();

    public static final String EVENT_DATE_TIME_STR = "2021-01-01T12:01:02.001+02:00";
    public static final String EVENT_PID_STR = "1000";
    public static final String EVENT_DESCRIPTION = "job started";
}
