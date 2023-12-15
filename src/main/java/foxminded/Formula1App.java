package foxminded;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class Formula1App {
    private static final String ABBREVIATIONS_FILE = "src\\main\\resources\\abbreviations.txt";
    private static final String START_LOG_FILE = "src\\main\\resources\\start.log";
    private static final String END_LOG_FILE = "src\\main\\resources\\end.log";

    public static void main(String[] args) {
        getFormula1Report();
    }

    private static void getFormula1Report(){
        try {
            Map<String, String> racerDetails = FileReader.readAbbreviations(ABBREVIATIONS_FILE);
            Map<String, LocalDateTime> startTimes = FileReader.readLog(START_LOG_FILE);
            Map<String, LocalDateTime> endTimes = FileReader.readLog(END_LOG_FILE);

            List<RacerResult> results = LapTimeCalculator.calculateLapTimes(racerDetails, startTimes, endTimes);
            ResultPrinter.printTopRacers(results);
        } catch (IOException e) {
            System.err.println("Error reading files: " + e.getMessage());
        }
    }
}
