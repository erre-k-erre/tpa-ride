package tpa.ride.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import tpa.ride.domain.exception.InvalidTicketException;

import java.time.Instant;

@RequiredArgsConstructor
@Getter
public abstract class Ticket {

    protected final Instant issueTime;

    protected final Instant expiryTime;

    public abstract boolean isValid();

    public abstract Ticket validate() throws InvalidTicketException;

}
