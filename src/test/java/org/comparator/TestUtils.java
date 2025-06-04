package org.comparator;

import java.io.File;

public class TestUtils {
    public static final String WELL_FORMED_EVENTS_FILE = new File("src/test/resources/well-formed-events.log").getAbsolutePath();
    public static final String MALFORMED_EVENTS_FILE = new File("src/test/resources/malformed-events.log").getAbsolutePath();

    public static final String EVENT_STAMP_STR = "12:00:00.000";
    public static final String EVENT_PID_STR = "1000";
    public static final String EVENT_DESCRIPTION = "job started";
}
