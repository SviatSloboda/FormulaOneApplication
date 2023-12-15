package foxminded;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class LapTimeCalculator {
    LapTimeCalculator(){
        throw new IllegalStateException();
    }

    public static List<RacerResult> calculateLapTimes(Map<String, String> racerDetails, Map<String, LocalDateTime> startTimes, Map<String, LocalDateTime> endTimes) {
        List<RacerResult> results = new ArrayList<>();

        racerDetails.forEach((abbreviation, details) -> {
            LocalDateTime startTime = startTimes.get(abbreviation);
            LocalDateTime endTime = endTimes.get(abbreviation);

            if (startTime != null && endTime != null && endTime.isAfter(startTime)) {
                Duration lapTime = Duration.between(startTime, endTime);
                String[] racerInfo = details.split("_");
                results.add(new RacerResult(racerInfo[0], racerInfo[1], lapTime));
            }
        });

        results.sort(Comparator.comparing(RacerResult::getLapTime));
        return results;
    }
}
