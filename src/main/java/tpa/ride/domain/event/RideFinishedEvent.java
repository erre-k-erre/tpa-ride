package tpa.ride.domain.event;

import lombok.Value;

import java.time.Instant;
import java.util.UUID;

@Value
public class RideFinishedEvent {

    private UUID customerId;

    private Instant entryTime;

    private Instant exitTime;

}
