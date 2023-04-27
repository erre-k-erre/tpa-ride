package tpa.ride.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class StationStatsRecord {

    private LocalDate date;

    private Station station;

    private long entries;

    private long exits;

    public long addEntry() {
        return entries++;
    }

    public long addExit() {
        return exits++;
    }

    public static StationStatsRecord of(LocalDate date, Station station) {
        return new StationStatsRecord(date, station, 0, 0);
    }

}
