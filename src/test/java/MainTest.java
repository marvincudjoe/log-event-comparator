import org.junit.jupiter.api.Test;
import org.monitor.Main;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MainTest {

    @Test
    void happyPath() {
        String path = new File("src/test/resources/logs.log").getAbsolutePath();
        System.setIn(new ByteArrayInputStream(path.getBytes()));
        assertDoesNotThrow(() -> Main.main(new String[]{}));
    }

    @Test
    void invalidFilePath() {
        System.setIn(new ByteArrayInputStream(new File("src/test/resources/logs.lo").getAbsolutePath().getBytes()));
        assertThrows(FileNotFoundException.class, () -> Main.main(new String[]{}));
    }
}