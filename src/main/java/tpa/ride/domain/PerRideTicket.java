package tpa.ride.domain;

import lombok.EqualsAndHashCode;
import lombok.Value;
import tpa.ride.domain.exception.NoRemainingRidesException;

import java.time.Instant;

@Value
@EqualsAndHashCode(callSuper = true)
public class PerRideTicket extends Ticket {

    private final int totalRides;

    private final int remainingRides;

    private PerRideTicket(Instant issueDate, int totalRides) {
        super(issueDate, null);
        this.totalRides = totalRides;
        this.remainingRides = totalRides;
    }

    public PerRideTicket(Instant issueTime, int totalRides, int remainingRides) {
        super(issueTime, remainingRides > 0 ? null : Instant.now());
        this.totalRides = totalRides;
        this.remainingRides = remainingRides;
    }

    @Override
    public boolean isValid() {
        return remainingRides > 0;
    }

    @Override
    public PerRideTicket validate() throws NoRemainingRidesException {
        return new PerRideTicket(this.issueTime, this.totalRides, this.totalRides - 1);
    }

    public static PerRideTicket singleRide() {
        return new PerRideTicket(Instant.now(), 1);
    }

    public static PerRideTicket tenRides() {
        return new PerRideTicket(Instant.now(), 10);
    }

}
