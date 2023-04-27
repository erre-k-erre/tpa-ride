package tpa.ride.domain;

import lombok.EqualsAndHashCode;
import lombok.Value;

import java.time.Instant;

import static java.time.temporal.ChronoUnit.DAYS;

@Value
@EqualsAndHashCode(callSuper = true)
public class TemporalTicket extends Ticket {

    private TemporalTicket(Instant issueDate, int days) {
        super(issueDate, issueDate.plus(days, DAYS));
    }

    @Override
    public boolean isValid() {
        return super.getExpiryTime().isAfter(Instant.now());
    }

    @Override
    public TemporalTicket validate() {
        return this;
    }

    public static TemporalTicket daily() {
        return new TemporalTicket(Instant.now(), 1);
    }

    public static TemporalTicket weekly() {
        return new TemporalTicket(Instant.now(), 7);
    }

    public static TemporalTicket monthly() {
        return new TemporalTicket(Instant.now(), 30);
    }

}
