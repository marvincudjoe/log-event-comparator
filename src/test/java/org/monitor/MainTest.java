package org.monitor;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.monitor.TestUtils.INVALID_LOG_FILE;
import static org.monitor.TestUtils.VALID_LOG_FILE;

class MainTest {

    @Test
    void happyPath() {
        System.setIn(new ByteArrayInputStream(VALID_LOG_FILE.getBytes()));
        assertDoesNotThrow(() -> Main.main(new String[]{}));
    }

    @Test
    void invalidFilePath() {
        System.setIn(new ByteArrayInputStream(VALID_LOG_FILE.substring(2,3).getBytes()));
        assertThrows(FileNotFoundException.class, () -> Main.main(new String[]{}));
    }
}