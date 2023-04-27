package tpa.ride.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CustomerStatsRecord {

    private LocalDate date;

    private UUID customerId;

    private long numberOfRides;

    private long totalTravelTimeInMinutes;

    public void addRide(long totalMinutes) {

        numberOfRides ++;
        totalTravelTimeInMinutes += totalMinutes;

    }

    public static CustomerStatsRecord of(LocalDate date, UUID customerId) {
        return new CustomerStatsRecord(date, customerId, 0, 0);
    }

}
