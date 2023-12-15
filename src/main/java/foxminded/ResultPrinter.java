package foxminded;

import java.time.Duration;
import java.util.List;

public class ResultPrinter {
    ResultPrinter(){
        throw new IllegalStateException();
    }

    public static void printTopRacers(List<RacerResult> results) {
        int topCount = 15;

        for (int i = 0; i < results.size(); i++) {
            if (i == topCount) {
                System.out.println("------------------------------------------------------------------------");
            }
            RacerResult result = results.get(i);
            System.out.printf("%2d. %-20s | %-30s | %s\n", i + 1, result.getRacerName(), result.getTeamName(), formatLapTime(result.getLapTime()));
        }
    }

    private static String formatLapTime(Duration lapTime) {
        long millis = lapTime.toMillis();
        long minutes = millis / 60000;
        long seconds = (millis % 60000) / 1000;
        long millisRemaining = millis % 1000;

        return String.format("%d:%02d.%03d", minutes, seconds, millisRemaining);
    }
}
