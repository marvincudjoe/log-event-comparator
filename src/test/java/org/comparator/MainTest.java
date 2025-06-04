package org.comparator;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.comparator.TestUtils.WELL_FORMED_EVENTS_FILE;

class MainTest {

    @Test
    void happyPath() {
        System.setIn(new ByteArrayInputStream(WELL_FORMED_EVENTS_FILE.getBytes()));
        assertDoesNotThrow(() -> Main.main(new String[]{}));
    }

    @Test
    void givenUnknownFilePath_throwException() {
        System.setIn(new ByteArrayInputStream(WELL_FORMED_EVENTS_FILE.substring(2, 3).getBytes()));
        assertThrows(FileNotFoundException.class, () -> Main.main(new String[]{}));
    }
}