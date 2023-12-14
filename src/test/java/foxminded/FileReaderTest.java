package foxminded;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.Map;

class FileReaderTest {
    @Test
    void testReadAbbreviations_when_EmptyFile_then_Empty() throws IOException {
        //given
        Path tempFile = Files.createTempFile("empty", ".txt");

        //when
        Map<String, String> racerDetails = FileReader.readAbbreviations(tempFile.toString());

        //then
        assertTrue(racerDetails.isEmpty());

        Files.delete(tempFile);
    }

    @Test
    void testReadLog_whenValidLogData_thenMapOfLogTimes() throws IOException {
        //given
        Path tempFile = Files.createTempFile("log", ".txt");
        Files.write(tempFile, "RAC2019-01-01_10:00:00.000\nDRI2019-01-01_12:30:45.123\n".getBytes(), StandardOpenOption.WRITE);

        //when
        Map<String, LocalDateTime> logTimes = FileReader.readLog(tempFile.toString());

        //then
        assertEquals(2, logTimes.size());
        assertTrue(logTimes.containsKey("RAC"));
        assertTrue(logTimes.containsKey("DRI"));
        assertEquals(LocalDateTime.of(2019, 1, 1, 10, 0, 0, 0), logTimes.get("RAC"));
        assertEquals(LocalDateTime.of(2019, 1, 1, 12, 30, 45, 123_000_000), logTimes.get("DRI"));

        Files.delete(tempFile);
    }

    @Test
    void testReadLog_whenInvalidLogData_thenDateTimeParseException() throws IOException {
        //given
        Path tempFile = Files.createTempFile("invalidDateFormat", ".txt");

        //when
        Files.write(tempFile, "RAC2019-01-01_10:00:00.000\nDRIinvalidDate\n".getBytes(), StandardOpenOption.WRITE);

        //then
        assertThrows(
            java.time.format.DateTimeParseException.class,
            () -> FileReader.readLog(tempFile.toString())
        );

        Files.delete(tempFile);
    }
}
