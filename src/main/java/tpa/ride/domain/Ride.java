package tpa.ride.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import tpa.ride.domain.event.RideFinishedEvent;

import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Ride {

    @Getter
    public UUID id;

    @Getter
    public UUID customerId;

    @Getter
    private TicketValidator entryTicketValidator;

    @Getter
    private TicketValidator exitTicketValidator;

    @Getter
    private Instant entryTime;

    @Getter
    private Instant exitTime;

    @Getter
    private boolean active;

    public void finish(TicketValidator ticketValidator) {

        this.exitTicketValidator = ticketValidator;
        this.exitTime = Instant.now();
        this.active = false;

        Subscriptions.dispatch(new RideFinishedEvent(customerId, entryTime, exitTime));

    }

    public static Ride of(UUID customerId, TicketValidator entryTicketValidator) {
        return new Ride(UUID.randomUUID(), customerId, entryTicketValidator, null, Instant.now(), null, true);
    }

}
