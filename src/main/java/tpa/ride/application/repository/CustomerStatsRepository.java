package tpa.ride.application.repository;

import tpa.ride.domain.CustomerStatsRecord;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public interface CustomerStatsRepository {

    void updateRecord(CustomerStatsRecord record);

    Optional<CustomerStatsRecord> findRecord(UUID customerId, LocalDate date);

}
