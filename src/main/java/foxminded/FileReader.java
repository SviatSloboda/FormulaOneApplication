package foxminded;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class FileReader {
    FileReader(){
        throw new IllegalStateException();
    }

    public static Map<String, String> readAbbreviations(String fileName) throws IOException {
        Map<String, String> racerDetails = new HashMap<>();

        try (Stream<String> lines = Files.lines(Paths.get(fileName))) {
            lines.forEach(line -> {
                String[] parts = line.split("_");
                if (parts.length == 3) {
                    racerDetails.put(parts[0], parts[1] + "_" + parts[2]);
                }
            });
        }

        return racerDetails;
    }

    public static Map<String, LocalDateTime> readLog(String fileName) throws IOException {
        Map<String, LocalDateTime> logTimes = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss.SSS");

        try (Stream<String> lines = Files.lines(Paths.get(fileName))) {
            lines.forEach(line -> {
                String[] parts = line.split("[A-Z]{3}");
                if (parts.length == 2) {
                    String abbreviation = line.substring(0, 3);
                    LocalDateTime time = LocalDateTime.parse(parts[1], formatter);
                    logTimes.put(abbreviation, time);
                }
            });
        }

        return logTimes;
    }
}
