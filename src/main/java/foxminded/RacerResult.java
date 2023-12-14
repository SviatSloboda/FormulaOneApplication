package foxminded;

import java.time.Duration;
import java.util.Objects;

public class RacerResult {
    private final String racerName;
    private final String teamName;
    private final Duration lapTime;

    public RacerResult(String racerName, String teamName, Duration lapTime) {
        this.racerName = racerName;
        this.teamName = teamName;
        this.lapTime = lapTime;
    }

    public String getRacerName() {
        return racerName;
    }

    public String getTeamName() {
        return teamName;
    }

    public Duration getLapTime() {
        return lapTime;
    }

    @Override
    public String toString() {
        long millis = lapTime.toMillis();
        long minutes = millis / 60000;
        long seconds = (millis % 60000) / 1000;
        long millisRemaining = millis % 1000;

        return String.format("%s | %s | %d:%02d.%03d", racerName, teamName, minutes, seconds, millisRemaining);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RacerResult that = (RacerResult) o;
        return Objects.equals(racerName, that.racerName) && Objects.equals(teamName, that.teamName) && Objects.equals(lapTime, that.lapTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(racerName, teamName, lapTime);
    }
}