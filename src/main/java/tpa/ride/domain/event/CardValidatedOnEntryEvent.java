package tpa.ride.domain.event;

import lombok.Value;
import tpa.ride.domain.Identification;
import tpa.ride.domain.TicketValidator;

import java.time.Instant;
import java.util.UUID;

@Value
public class CardValidatedOnEntryEvent {

    private final UUID customerId;

    private final Identification identification;

    private final TicketValidator ticketValidator;

    private final Instant entryTime;

}
