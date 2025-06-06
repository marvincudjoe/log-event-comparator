package org.comparator;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.comparator.TestUtils.WELL_FORMED_EVENTS_FILE;

class MainTest {

    @Test
    void happyPath() {
        assertDoesNotThrow(() -> Main.main(new String[]{"--file=" + WELL_FORMED_EVENTS_FILE}));
    }

    @Test
    void givenUnknownFilePath_throwException() {
        assertThrows(FileNotFoundException.class, () -> Main.main(new String[]{"--file=" + "g.log"}));
    }
}