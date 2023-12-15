package foxminded;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class LapTimeCalculatorTest {

    @Test
    void testCalculateLapTimes_whenValidData_thenCorrectLapTime() {
        //given
        Map<String, String> racerDetails = new HashMap<>();
        racerDetails.put("SVF", "Sebastian Vettel_FERRARI");

        Map<String, LocalDateTime> startTimes = new HashMap<>();
        startTimes.put("SVF", LocalDateTime.of(2022, 3, 21, 14, 30));

        Map<String, LocalDateTime> endTimes = new HashMap<>();
        endTimes.put("SVF", LocalDateTime.of(2022, 3, 21, 14, 31, 30));

        //when
        List<RacerResult> results = LapTimeCalculator.calculateLapTimes(racerDetails, startTimes, endTimes);

        //then
        assertEquals(1, results.size());
        assertEquals(90, results.get(0).getLapTime().getSeconds(), "Lap time should be 90 seconds");
    }

    @Test
    void testCalculateLapTimes_whenMultipleRacers_thenSortResultsCorrectly() {
        //given
        Map<String, String> racerDetails = new HashMap<>();
        racerDetails.put("SVF", "Sebastian Vettel_FERRARI");
        racerDetails.put("LHM", "Lewis Hamilton_MERCEDES");

        Map<String, LocalDateTime> startTimes = new HashMap<>();
        startTimes.put("SVF", LocalDateTime.of(2022, 3, 21, 14, 30));
        startTimes.put("LHM", LocalDateTime.of(2022, 3, 21, 14, 32));

        Map<String, LocalDateTime> endTimes = new HashMap<>();
        endTimes.put("SVF", LocalDateTime.of(2022, 3, 21, 14, 31));
        endTimes.put("LHM", LocalDateTime.of(2022, 3, 21, 14, 32, 30));

        //when
        List<RacerResult> results = LapTimeCalculator.calculateLapTimes(racerDetails, startTimes, endTimes);

        //then
        assertEquals("Lewis Hamilton", results.get(0).getRacerName(), "Lewis Hamilton should be first due to faster lap");
        assertEquals("Sebastian Vettel", results.get(1).getRacerName(), "Sebastian Vettel should be second");
    }

    @Test
    void testCalculateLapTimes_whenMissingTimes_thenExcludeRacer() {
        //given
        Map<String, String> racerDetails = new HashMap<>();
        racerDetails.put("SVF", "Sebastian Vettel_FERRARI");
        racerDetails.put("LHM", "Lewis Hamilton_MERCEDES");

        Map<String, LocalDateTime> startTimes = new HashMap<>();
        startTimes.put("SVF", LocalDateTime.of(2022, 3, 21, 14, 30));

        Map<String, LocalDateTime> endTimes = new HashMap<>();
        endTimes.put("SVF", LocalDateTime.of(2022, 3, 21, 14, 31));
        endTimes.put("LHM", LocalDateTime.of(2022, 3, 21, 14, 32));

        //when
        List<RacerResult> results = LapTimeCalculator.calculateLapTimes(racerDetails, startTimes, endTimes);

        //then
        assertEquals(1, results.size(), "Only racers with both start and end times should be included");
        assertEquals("Sebastian Vettel", results.get(0).getRacerName());
    }

    @Test
    void testCalculateLapTimes_whenStartTimeAfterEndTime_thenExcludeRacer() {
        //given
        Map<String, String> racerDetails = new HashMap<>();
        racerDetails.put("SVF", "Sebastian Vettel_FERRARI");

        Map<String, LocalDateTime> startTimes = new HashMap<>();
        startTimes.put("SVF", LocalDateTime.of(2022, 3, 21, 14, 32));

        Map<String, LocalDateTime> endTimes = new HashMap<>();
        endTimes.put("SVF", LocalDateTime.of(2022, 3, 21, 14, 31));

        //when
        List<RacerResult> results = LapTimeCalculator.calculateLapTimes(racerDetails, startTimes, endTimes);

        //then
        assertTrue(results.isEmpty(), "No results should be included if start time is after end time");
    }
}