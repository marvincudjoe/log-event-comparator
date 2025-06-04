package org.comparator.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.comparator.model.EventDetails;

import java.io.FileNotFoundException;
import java.util.List;

import static org.comparator.TestUtils.MALFORMED_EVENTS_FILE;
import static org.comparator.TestUtils.WELL_FORMED_EVENTS_FILE;

class LogFileReaderTest {

    @Test
    void givenValidFile_returnEvents() throws FileNotFoundException {
        List<EventDetails> eventDetailsList = LogFileReader.getEvents(WELL_FORMED_EVENTS_FILE);
        Assertions.assertEquals(13, eventDetailsList.size());
    }

    @Test
    void givenInvalidFilePath_throwsException() {
        var filePath = WELL_FORMED_EVENTS_FILE.substring(1, 2);
        var err = Assertions
                .assertThrows(FileNotFoundException.class, () -> LogFileReader.getEvents(filePath));
        Assertions.assertEquals(String.format(LogFileReader.INVALID_PATH_FILE_PROVIDED, filePath), err.getMessage());
    }

    @Test
    void givenMalformedEvents_returnEmptyList() throws FileNotFoundException {
        List<EventDetails> eventDetailsList = LogFileReader.getEvents(MALFORMED_EVENTS_FILE);
        Assertions.assertEquals(0, eventDetailsList.size());
    }

}
