package tpa.ride.application.repository;

import tpa.ride.domain.Station;
import tpa.ride.domain.StationStatsRecord;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public interface StationStatsRepository {

    void updateRecord(StationStatsRecord record);

    Optional<StationStatsRecord> findRecord(Station station, LocalDate date);

}
