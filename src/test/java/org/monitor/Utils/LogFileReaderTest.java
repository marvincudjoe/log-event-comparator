package org.monitor.Utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.monitor.model.EventDetails;
import org.monitor.utils.LogFileReader;

import java.io.FileNotFoundException;
import java.util.List;

import static org.monitor.TestUtils.VALID_LOG_FILE;

class LogFileReaderTest {

    @Test
    void getEvents() throws FileNotFoundException {
        List<EventDetails> eventDetailsList = LogFileReader.getEvents(VALID_LOG_FILE);
        Assertions.assertEquals(13, eventDetailsList.size());
    }
}